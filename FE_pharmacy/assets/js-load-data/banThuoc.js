$(document).ready(function () {
    let debounceTimer;
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
                //console.log("API Response:", response); 
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

    let productList = [];
    // Xử lý sự kiện click vào từng item trong danh sách gợi ý
    $(document).on("click", "#suggestionList li", function () {
        var medicineId = $(this).data("id");
        $.ajax({
            url: `http://localhost:1122/medicine/getById/${medicineId}`,
            type: 'GET',
            success: function (response) {
                const value = response.data;
                productList.push(value);
                CreateObject(productList);
                //console.log("Full response:", JSON.stringify(response, null, 2));

                var html = `<tr data-id="${value.id}" class="medicineInvoice">
                            <td>${value.codeMedicine}</td>
                            <td>${value.nameMedicine} hỗ trợ ${value.uses} 
                            (${value.packagingMethod}) - Số lượng: ${value.quantity} 
                            - Loại: ${value.prescriptionMedicine ? 'Kê đơn' : 'Không kê đơn'}</td>
                            <td>${value.prescriptionMedicine == true ?
                        '<i class="fa-solid fa-check" style="color: green;"></i>' :
                        '<i class="fa-solid fa-xmark" style="color: red;"></i>'}</td>
                            <td>${value.unitOfCalculation}</td>
                            <td style="color: rgb(119, 117, 117);">
                                <input type="number" class="quantity" name="quantity" min="1" style="width: 50px; text-align: center;" value="1">
                                Kho: <span class="quantity-wh">${value.quantity}</span>
                            </td>
                            <td>${formatCurrency(value.price)}</td>
                            <td class="total-price">
                                ${formatCurrency(value.price)} 
                            </td>
                            <td>
                                <i class="fa-solid fa-circle-minus" data-id="${value.id}" style="color: red;"></i>
                            </td>
                        </tr>`;
                $('#loadData').append(html);

                // Cập nhật thành tiền ngay sau khi load
                updateTotalPrice(value.id, value.price, 1);

                // Lắng nghe sự kiện thay đổi số lượng
                $("#loadData").on("input", ".quantity", function () {
                    var quantity = $(this).val();
                    if (quantity < 0) quantity = 0;
                    var productId = $(this).closest('tr').data('id');
                    updateTotalPrice(productId, value.price, quantity); // Cập nhật lại thành tiền cho sản phẩm này
                });
            },
            error: function (err) {
                console.error("Lỗi khi gọi API getById:", err);
            }
        });
        $("#suggestionList").hide();
    });

    // Hàm cập nhật thành tiền cho từng sản phẩm
    function updateTotalPrice(productId, price, quantity) {
        var totalPrice = price * quantity;
        var totalPriceElement = $("tr[data-id='" + productId + "'] .total-price");

        totalPriceElement.text(formatCurrency(totalPrice));

        let calTotalPrice = 0;
        $('.total-price').each(function () {
            let priceText = $(this).text().replace(/[^\d.-]/g, '');
            let price = parseFloat(priceText);
            if (!isNaN(price)) {
                calTotalPrice += price;
            }
        });
        $('#totalPrice').text(formatCurrency(calTotalPrice * 1000));
    }

    $(document).on("click", ".fa-circle-minus", function () {
        var productId = $(this).data("id");
        var row = $(this).closest("tr");
        row.remove();
    });


    function CreateObject(productList) {
        // Lấy giá trị từ các trường nhập liệu
        let nameCustomer = $('#customer-name').val();
        let phone = $('#customer-phone').val();
        let location = $('#location').val();
        let examPaper = $('#exam-paper').val();

        let information = {
            customerName: nameCustomer,
            phone: phone,
            examinationCode: examPaper,
            examinationPlace: location,
            productList: productList
        };

        console.log("Full response:", JSON.stringify(information, null, 2));
        return information;
    }


});
