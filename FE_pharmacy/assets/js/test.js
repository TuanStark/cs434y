$(document).ready(function () {
    let debounceTimer;
    let productList = [];
    let medicineList = [];

    // Format tiền tệ
    function formatCurrency(value) {
        if (isNaN(value)) {
            return '';
        }
        return value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
    }

    function fetchSuggestions(keyword) {
        if (!keyword || keyword.trim() === "") {
            $("#suggestionList").empty().hide();
            return;
        }

        $.ajax({
            url: `http://localhost:1122/medicine/search?keyword=${keyword}`,
            type: 'GET',
            success: function (response) {
                $("#suggestionList").empty();
                if (response.data && response.data.length > 0) {
                    $.each(response.data, function (index, value) {
                        const html = `
                            <li data-id="${value.id}">
                                ${value.nameMedicine} hỗ trợ ${value.uses} 
                                (${value.packagingMethod}) - Số lượng: ${value.quantity} 
                                - Loại: ${value.prescriptionMedicine ? 'Kê đơn' : 'Không kê đơn'}
                            </li>`;
                        $("#suggestionList").append(html);
                    });
                    $("#suggestionList").show();
                } else {
                    $("#suggestionList").append("<li>Không tìm thấy kết quả</li>").show();
                }
            },
            error: function (err) {
                console.error("Lỗi khi gọi API:", err);
                $("#suggestionList").empty().append("<li>Đã xảy ra lỗi, vui lòng thử lại sau.</li>").show();
            }
        });
    }

    $("#searchInput").on("input", function () {
        const keyword = $(this).val();
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(function () {
            fetchSuggestions(keyword);
        }, 300);
    });

    $(document).on("click", function (event) {
        if (!$(event.target).closest("#searchInput, #suggestionList").length) {
            $("#suggestionList").hide();
        }
    });


    $(document).on("click", "#suggestionList li", function () {
        $("#searchInput").val('');// xóa dữ liệu ở ô tìm kiếm
    });

    $(document).on("click", "#suggestionList li", function () {
        const medicineId = $(this).data("id");
        $.ajax({
            url: `http://localhost:1122/medicine/getById/${medicineId}`,
            type: 'GET',
            success: function (response) {
                const value = response.data;
                productList.push(value);

                const html = `<tr data-id="${value.id}" class="medicineInvoice">
                                <td>${value.codeMedicine}</td>
                                <td>${value.nameMedicine} hỗ trợ ${value.uses} 
                                (${value.packagingMethod}) - Số lượng: ${value.quantity} 
                                - Loại: ${value.prescriptionMedicine ? 'Kê đơn' : 'Không kê đơn'}</td>
                                <td>${value.prescriptionMedicine ? '<i class="fa-solid fa-check" style="color: green;"></i>' : '<i class="fa-solid fa-xmark" style="color: red;"></i>'}</td>
                                <td class="uOc">${value.unitOfCalculation}</td>
                                <td style="color: rgb(119, 117, 117);">
                                    <input type="number" class="quantity" name="quantity" min="1" style="width: 50px; text-align: center;" value="1">
                                    Kho: <span class="quantity-wh">${value.quantity}</span>
                                </td>
                                <td class="unit-price">
                                    ${formatCurrency(value.price)}
                                </td>
                                <td class="total-price">
                                    ${formatCurrency(value.price)} 
                                </td>
                                <td>
                                    <i class="fa-solid fa-circle-minus" data-id="${value.id}" style="color: red;"></i>
                                </td>
                            </tr>`;
                $('#loadData').append(html);

                updateTotalPrice(value.id, value.price, 1);
            },
            error: function (err) {
                console.error("Lỗi khi gọi API getById:", err);
            }
        });
        $("#suggestionList").hide();
    });

    $(document).on("click", ".fa-circle-minus", function () {
        const productId = $(this).data("id");
        $(this).closest("tr").remove();
        productList = productList.filter(product => product.id !== productId);
        saveInvoiceToLocalStorage();
        updateTotalPrice();
    });

    function saveInvoiceToLocalStorage() {
        const customerInfo = {
            customerName: $('#customer-name').val(),
            phone: $('#customer-phone').val(),
            examinationCode: $('#exam-paper').val(),
            examinationPlace: $('#location').val(),
        };

        medicineList = [];
        $('#loadData tr').each(function () {
            const id = $(this).data('id');
            const quantity = parseInt($(this).find('.quantity').val());
            const unitPrice = parseFloat($(this).find('.unit-price').text().replace(/[^\d.-]/g, ''));
            const unitOfCalculator = $(this).find('.uOc').text().trim();
            const totalPrice = parseFloat($(this).find('.total-price').text().replace(/[^\d.-]/g, ''));

            medicineList.push({ id, quantity, unitPrice, unitOfCalculator, totalPrice });
        });

        const invoice = {
            customerInfo,
            medicinList: medicineList,
            totalPrice: $('#totalPrice').text(),
        };

        //localStorage.setItem('invoice', JSON.stringify(invoice));
        //console.log("medicine List:", invoice);
    }

    function updateTotalPrice(productId, price, quantity) {
        if (productId) {
            const totalPrice = price * quantity;
            const totalPriceElement = $(`tr[data-id='${productId}'] .total-price`);
            totalPriceElement.text(formatCurrency(totalPrice));
        }

        let calTotalPrice = 0;
        $('.total-price').each(function () {
            let priceText = $(this).text().replace(/[^\d.-]/g, '');
            let price = parseFloat(priceText);
            if (!isNaN(price)) {
                calTotalPrice += price;
            }
        });
        $('#totalPrice').text(formatCurrency(calTotalPrice));

        saveInvoiceToLocalStorage();
    }

    $('#loadData').on('input', '.quantity', function () {
        const quantity = Math.max(0, parseInt($(this).val()) || 0);
        const productId = $(this).closest('tr').data('id');
        const unitPrice = parseFloat($(this).closest('tr').find('td:nth-child(6)').text().replace(/[^\d.-]/g, ''));
        updateTotalPrice(productId, unitPrice, quantity);
    });

    $('#createInvoiceButton').on('click', function () {
        const customerInfo = {
            customerName: $('#customer-name').val().trim(),
            phone: $('#customer-phone').val().trim(),
            examinationCode: $('#exam-paper').val().trim(),
            examinationPlace: $('#location').val().trim(),
        };
        if (!customerInfo.customerName || !customerInfo.phone || !customerInfo.examinationPlace) {
            alert('Vui lòng nhập đầy đủ thông tin khách hàng!');
            return;
        }
        if (productList.length === 0) {
            alert('Danh sách thuốc trống, vui lòng thêm thuốc vào hóa đơn!');
            return;
        }
        let totalMoney = 0;

        const invoiceDetails = medicineList.map(product => {
            const quantity = parseInt($(`tr[data-id='${product.id}'] .quantity`).val());
            const unitPrice = parseFloat(product.unitPrice);
            const unitOfCalculator = product.unitOfCalculator;
            const totalPrice = unitPrice * quantity;
            totalMoney += totalPrice;

            return {
                medicineID: product.id,
                unitOfCalculator: unitOfCalculator,
                quantity: quantity,
                price: unitPrice * 1000,
                totalMoney: totalPrice * 1000
            };
        });

        const invoiceData = {
            typeInvoice: 2,
            totalMoney: totalMoney * 1000,
            saleInvoiceID: customerInfo,
            invoiceDetailRequestList: invoiceDetails,
            accountID: 3
        };

        if (invoiceData.typeInvoice === 0) {
            alert('TypeInvoice không hợp lệ');
            return;
        }
        $.ajax({
            method: 'POST',
            url: `http://localhost:1122/invoice/create`,
            contentType: 'application/json',
            data: JSON.stringify(invoiceData),
            beforeSend: function () {
                $('#createInvoiceButton').prop('disabled', true).text('Đang gửi...');
            },
            success: function (response) {
                console.log(response)
                alert('Thêm hóa đơn bán thành công!');
                location.reload();
                // localStorage.removeItem('invoice');
            },
            error: function (xhr) {
                alert('Có lỗi xảy ra: ' + xhr.responseText);
                console.error(xhr);
            },
            complete: function () {
                $('#createInvoiceButton').prop('disabled', false).text('Tạo hóa đơn');
            }
        });
        console.log("Hóa đơn gửi đi:", JSON.stringify(invoiceData, null, 2));
    });
});
