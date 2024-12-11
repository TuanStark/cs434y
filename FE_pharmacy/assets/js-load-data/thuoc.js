$(document).ready(function () {
    var size = 5;
    loadPage();

    //forrmat date
    function formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('vi-VN', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric'
        });
    }
    $(document).on('click', '.page-link', function (e) {
        e.preventDefault();
        var page = $(this).data('page');
        if (page) {
            loadPage(page);
        }
    });

    function loadPage(page = 1) {
        var link = `http://localhost:1122/medicine/all?page=${page}&size=${size}`;
        $.ajax({
            method: "GET",
            url: link
        })
            .done(function (msg) {
                $("#getAll").empty();
                if (msg.data.data != null) {
                    var response = msg.data.data;
                    //console.log("Full response:", JSON.stringify(response, null, 2));
                    $.each(response, function (index, value) {
                        var stt = (msg.data.currentPage - 1) * msg.data.pageSize + index + 1;
                        var html = `<tr>
                            <td>${stt}</td>
                            <td>${value.codeMedicine}</td>
                            <td>${value.nameMedicine}</td>
                            <td>${value.prescriptionMedicine == 1 ? '<i class="fa-solid fa-check" style="color: green;"></i>' : '<i class="fas fa-times text-danger"></i>'}</td>
                            <td>${value.type.name}</td>
                            <td>${value.quantity}</td>
                            <td style="display: flex;justify-content: space-between;">
                                <a data-id="${value.id}" id="btnAction" data-action="view" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal"
                        data-bs-whatever="@mdo">Xem</a>
                                <a href="/ChinhSuaThongTinThuoc.html?id=${value.id}" data-id="${value.id}" id="btnEdit" data-action="edit" class="btn btn-warning">Cập nhật</a>
                                <a data-id="${value.id}" id="btnDelete" class="btn btn-danger">Xóa</a>
                            </td>
                        </tr>`;
                        $("#getAll").append(html);
                    });

                    var currentPage = msg.data.currentPage;
                    var totalPages = msg.data.totalPage;

                    // Cấu trúc giao diện phân trang
                    var pagination = `<nav aria-label="Page navigation example">
                                    <ul class="pagination">`;

                    // Nút 'Previous'
                    pagination += `<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="#" data-page="${currentPage - 1}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>`;

                    // Hiển thị số trang
                    for (var i = 1; i <= totalPages; i++) {
                        pagination += `<li class="page-item ${i == currentPage ? 'active' : ''}">
                                       <a class="page-link" href="#" data-page="${i}">${i}</a>
                                   </li>`;
                    }

                    // Nút 'Next'
                    pagination += `<li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="#" data-page="${currentPage + 1}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>`;

                    pagination += `</ul></nav>`;

                    $("#paginationContainer").html(pagination);
                } else {
                    $(".dataTables_empty").show();
                    $("#paginationContainer").hide();
                }
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                console.error("Error:", textStatus, errorThrown);
            });
    }
    loadComboBox();
    // đổ dữu liệu vào combobox
    function loadComboBox(id) {
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

    $(document).on('click', '#btnAdd', function (e) {
        $('#title').text('Thêm thuốc');
        resetForm();
        $('#name').prop('readonly', false);
        $('#dosage').prop('readonly', false);
        $('#uses').prop('readonly', false);
        $('#calculatorOfunit').prop('readonly', false);
        $('#packagingMethod').prop('readonly', false);
        $('#description').prop('readonly', false);
        $('#sideEffects').prop('readonly', false);
        $('#instructionsUse').prop('readonly', false);
        $('#description').prop('readonly', false);
        $('#cbTypeMedicine').prop('disabled', false);
        $('#cbPrescriptionMedicine').prop('disabled', false);
        $('#attention').prop('readonly', false);
        $('#ingredient').prop('readonly', false);
    })

    $(document).on('click', '#btnAction', function (e) {
        e.preventDefault();

        var id = $(this).data("id");
        $('#hiddenId').val(id);

        var id = $('#hiddenId').val();

        const action = $(this).data('action');
        const buttonText = $(this).text();

        if (action === 'view') {
            $('#title').text('Xem thông tin thuốc');
            $('#hiddenId').val();
            $('#btnOption').hide();

            // Đặt các trường nhập liệu ở chế độ readonly
            $('#name').prop('readonly', true);
            $('#dosage').prop('readonly', true);
            $('#uses').prop('readonly', true);
            $('#calculatorOfunit').prop('readonly', true);
            $('#packagingMethod').prop('readonly', true);
            $('#description').prop('readonly', true);
            $('#sideEffects').prop('readonly', true);
            $('#instructionsUse').prop('readonly', true);
            $('#description').prop('readonly', true);
            $('#cbTypeMedicine').prop('disabled', true);
            $('#cbPrescriptionMedicine').prop('disabled', true);
            $('#attention').prop('readonly', true);
            $('#ingredient').prop('readonly', true);

        } else if (action === 'edit') {
            $('#btnOption').show();
            $('#title').text('Cập nhật thuốc');

            // Đặt các trường nhập liệu ở chế độ 
            $('#name').prop('readonly', false);
            $('#dosage').prop('readonly', false);
            $('#uses').prop('readonly', false);
            $('#calculatorOfunit').prop('readonly', false);
            $('#packagingMethod').prop('readonly', false);
            $('#sideEffects').prop('readonly', false);
            $('#description').prop('readonly', false);
            $('#instructionsUse').prop('readonly', false);
            $('#description').prop('readonly', false);
            $('#cbTypeMedicine').prop('disabled', false);
            $('#cbPrescriptionMedicine').prop('disabled', false);
            $('#attention').prop('readonly', false);
            $('#ingredient').prop('readonly', false);
        }


        var id = $(this).data("id");
        $('#hiddenId').val(id);
        //console.log('id = ' + id);
        var baseUrl = 'http://localhost:1122/medicine';
        $.ajax({
            method: 'GET',
            url: `${baseUrl}/getById/${id}`,
            success: function (response) {
                //console.log("Full response:", JSON.stringify(response, null, 2));

                $('#name').val(response.data.nameMedicine);
                $('#dosage').val(response.data.dosage);
                $('#uses').val((response.data.uses));
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
                resetForm();
                alert('Không thể lấy thông tin ');
                console.error('Error:', xhr.responseText, status, error);
            }
        });
    });

    function resetForm() {
        $('#hiddenId').val('');
        $('#name').val('');
        $('#dosage').val('');
        $('#uses').val('');
        $('#calculatorOfunit').val('');
        $('#packagingMethod').val('');
        $('#sideEffects').val('');
        $('#instructionsUse').val('');
        $('#description').val('');
        $('#attention').val('');
        $('#ingredient').val('');
        $('#cbTypeMedicine').val('');
        $('#cbPrescriptionMedicine').val('');
    }

    $(document).on('click', '#btnDelete', function (e) {
        e.preventDefault();
        var id = $(this).data("id");
        if (confirm("Bạn có chắc chắn muốn xóa không?")) {
            $.ajax({
                method: "DELETE",
                url: `http://localhost:1122/medicine/delete/${id}`,
            })
                .done(function (msg) {
                    alert("Đã xóa thành công!");
                    loadPage(1);
                })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    console.error("Error:", textStatus, errorThrown);
                });
        }
    });


    $(document).on('click', '#btnSave', function (e) {
        e.preventDefault();

        var id = $('#hiddenId').val();
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
            method: 'POST',
            url: `http://localhost:1122/medicine/create`,
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                alert('Thêm thành công !');
                resetForm();
                $('#exampleModal').modal('hide');
                location.reload();
            },
            error: function (xhr) {
                alert('Có lỗi xảy ra: ' + xhr.responseText);
                console.error(xhr);
            }
        });
    });

});
