$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    const idAsNumber = urlParams.get('id');
    const idMedicine = parseInt(idAsNumber);

    $('#hiddenId').val(idMedicine);
    var id = $('#hiddenId').val();
    console.log(id);


    $('#btnImport1').click(function (e) {
        e.preventDefault();
        $('#importMedicine').show();
    });

    $(document).on('click', '#btnDestruct', function (e) {
        e.preventDefault();
        $('#importMedicine').hide();
    })

    $('#goBackBtn').click(function (e) {
        e.preventDefault();
        window.history.back();
    });
    loadComboBox();
    // đổ dữu liệu vào combobox
    function loadComboBox(id, combobox) {
        $.ajax({
            url: 'http://localhost:1122/type/all',
            method: 'GET',
            success: function (response) {
                var $combobox = $('#cbTypeMedicine');
                $combobox.empty();
                $.each(response.data.data, function (index, value) {
                    var isSelected = (value.id === id) ? 'selected' : '';
                    $combobox.append('<option value="' + value.id + '"' + isSelected + '>' + value.name + '</option>');
                });
            },
            error: function () {
                alert('Lỗi');
            }
        });
    }

    // load combobox nhà sản xuất
    loadComboBoxSupplier();
    function loadComboBoxSupplier(id) {
        $.ajax({
            url: 'http://localhost:1122/supplier/all',
            method: 'GET',
            success: function (response) {
                var $combobox = $('#cbSupplier');
                $combobox.empty();
                $.each(response.data.data, function (index, value) {
                    var isSelected = (value.id === id) ? 'selected' : '';
                    $combobox.append('<option value="' + value.id + '"' + isSelected + '>' + value.name + '</option>');
                });
            },
            error: function () {
                alert('Lỗi');
            }
        });
    }
    getMedicine();
    function getMedicine() {
        var baseUrl = 'http://localhost:1122/medicine';
        $.ajax({
            method: 'GET',
            url: `${baseUrl}/getById/${idMedicine}`,
            success: function (response) {
                //console.log("Full response:", JSON.stringify(response, null, 2));

                $('#name').val(response.data.nameMedicine);
                $('#dosage').val(response.data.dosage);
                $('#uses').val((response.data.uses));
                $('#quantity').val(response.data.quantity);
                $('#codeMedicine').val(response.data.codeMedicine);
                $('#calculatorOfunit').val(response.data.unitOfCalculation);
                $('#packagingMethod').val(response.data.packagingMethod);
                $('#sideEffects').val(response.data.sideEffects);
                $('#instructionsUse').val((response.data.instructionsUse));
                $('#description').val(response.data.description);
                $('#attention').val(response.data.attention);
                $('#ingredient').val(response.data.ingredient);
                loadComboBox(response.data.type.id);

                var isSelectedMale = response.data.prescriptionMedicine == 1 ? 'selected' : '';
                var isSelectedFemale = response.data.prescriptionMedicine == 0 ? 'selected' : '';
                $('#cbPrescriptionMedicine').empty();
                $('#cbPrescriptionMedicine').append('<option value="1" ' + isSelectedMale + '>Có</option>');
                $('#cbPrescriptionMedicine').append('<option value="0" ' + isSelectedFemale + '>không</option>');

            },
            error: function (xhr, status, error) {
                alert('Không thể lấy thông tin ');
                console.error('Error:', xhr.responseText, status, error);
            }
        });
    }

    $(document).on('click', '#btnEdit', function () {

        var nameMedicine = $('#name').val();
        var dosage = $('#dosage').val();
        var uses = $('#uses').val();
        var calculatorOfunit = $('#calculatorOfunit').val();
        var packagingMethod = $('#packagingMethod').val();
        var sideEffects = $('#sideEffects').val();
        var instructionsUse = $('#instructionsUse').val();
        var description = $('#description').val();
        var cbTypeMedicine = $('#cbTypeMedicine').val();
        var cbPrescriptionMedicine = $('#cbPrescriptionMedicine').val();
        var attention = $('#attention').val('');
        var ingredient = $('#ingredient').val('');

        var formData = new FormData();
        formData.append("nameMedicine", nameMedicine);
        formData.append("dosage", dosage);
        formData.append("uses", uses);
        formData.append("unitOfCalculation", calculatorOfunit);
        formData.append("packagingMethod", packagingMethod);
        formData.append("sideEffects", sideEffects);
        formData.append("instructionsUse", instructionsUse);
        formData.append("description", description);
        formData.append("attention", attention);
        formData.append("ingredient", ingredient);
        formData.append("prescriptionMedicine", cbPrescriptionMedicine == 1 ? true : false);
        formData.append("idType", cbTypeMedicine);

        $.ajax({
            method: 'PUT',
            url: `http://localhost:1122/medicine/update/${id}`,
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                alert('Cập nhật thành công !');
                window.history.back();
            },
            error: function (xhr) {
                alert('Có lỗi xảy ra khi cập nhật sản phẩm: ' + xhr.responseText);
                console.error(xhr);
            }
        });
    })

    $(document).on('click', '#btnImport', function (e) {
        e.preventDefault();
        var importDate = $('#importDate').val().trim();
        var productionDate = $('#productionDate').val().trim();
        var experationDate = $('#experationDate').val().trim();

        let date1 = new Date(importDate);
        let date2 = new Date(productionDate);
        let date3 = new Date(experationDate);

        var cbSupplier = $('#cbSupplier').val();
        console.log(cbSupplier);

        if (date3 < date2) {
            alert("Ngày hết hạn phải lớn hơn ngày sản xuất!");
        } else if (date1 > date3 || date1 < date2) {
            alert("Ngày nhập phải lớn hơn ngày sản xuất và bé hơn ngày hết hạn!");
        } else {
            const Info = {
                importDate: importDate,
                codeBatch: $('#codeBatch').val().trim(),
                productionDate: productionDate,
                sellingPrice: $('#sellingPrice').val().trim(),
                experationDate: experationDate,
                supplierId: cbSupplier
            };

            if (!Info.codeBatch || !Info.sellingPrice) {
                alert('Vui lòng nhập đầy đủ thông tin!');
                return;
            }

            let medicineList = [];
            var sellingPrice = parseFloat($('#sellingPrice').val().trim());
            var importPrice = parseFloat($('#importPrice').val().trim());

            let totalPrice = importPrice;
            let price = sellingPrice;

            const invoiceDetails = [
                {
                    medicineID: idMedicine,
                    unitOfCalculator: $('#calculatorOfunit').val(),
                    quantity: $('#quantity1').val(),
                    experationDate: experationDate,
                    price: parseFloat($('#sellingPrice').val()) * 1000,
                    totalMoney: parseFloat($('#importPrice').val()) * 1000
                }
            ];

            medicineList.push(invoiceDetails);

            const invoiceData = {
                typeInvoice: 1,
                totalMoney: totalPrice * 1000,
                importInvoiceID: Info,
                invoiceDetailRequestList: invoiceDetails,
                accountID: 3
            };

            $.ajax({
                method: 'POST',
                url: `http://localhost:1122/invoice/create`,
                contentType: 'application/json',
                data: JSON.stringify(invoiceData),
                success: function (response) {
                    console.log(response);
                    alert('Thêm hóa đơn bán thành công!');
                },
                error: function (xhr) {
                    alert('Có lỗi xảy ra: ' + xhr.responseText);
                    console.error(xhr);
                },
            });
        }
    });

});