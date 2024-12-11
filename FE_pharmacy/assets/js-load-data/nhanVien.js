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
        var link = `http://localhost:1122/account/all?page=${page}&size=${size}`;
        $.ajax({
            method: "GET",
            url: link
        })
            .done(function (msg) {
                $("#getAll").empty();
                if (msg.data.data != null) {
                    var response = msg.data.data;
                    // console.log("Full response:", JSON.stringify(response, null, 2));
                    //<td>${value.nameWorkShift}</td>
                    $.each(response, function (index, value) {
                        var stt = (msg.data.currentPage - 1) * msg.data.pageSize + index + 1;
                        var html = `<tr>
                            <td>${stt}</td>
                            <td>${value.fullName}</td>
                            <td>${value.phone}</td>
                            <td>${value.address}</td>
                            <td>${value.sex == 1 ? "Nam" : "Nữ"}</td>
                            <td>${formatDate(value.dateOfBirth)}</td>
                            
                            <td style="
                                    display: flex;
                                    justify-content: space-around;
                                ">
                                <a data-id="${value.id}" id="btnView" class="btn btn-info btnAction" data-action="view" data-bs-toggle="modal" data-bs-target="#exampleModal"
                        data-bs-whatever="@mdo">Xem</a>
                                <a data-id="${value.id}" id="btnEdit" class="btn btn-warning btnAction" data-action="edit" data-bs-toggle="modal" data-bs-target="#exampleModal"
                        data-bs-whatever="@mdo">Cập nhật</a>
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

    // đổ dữu liệu vào combobox
    function loadComboBox(id) {
        $.ajax({
            url: 'http://localhost:1122/ws/all',
            method: 'GET',
            success: function (response) {
                var $combobox = $('#cbWorkShift');
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
        $('#title').text('Thêm thông tin nhân viên');
        resetForm();
        $('#fullName1').prop('readonly', false);
        $('#phone').prop('readonly', false);
        $('#dateOfBirth').prop('readonly', false);
        $('#address').prop('readonly', false);
        $('#username').prop('readonly', false);
        $('#password').prop('readonly', false);
        $('#cbSex').prop('disabled', false);
        $('#cbWorkShift').prop('disabled', false);
    })

    function resetForm() {
        $('#hiddenId').val('');
        $('#fullName1').val('');
        $('#phone').val('');
        $('#dateOfBirth').val('');
        $('#address').val('');
        $('#username').val('');
        $('#password').val('');
        $('#cbSex').val('');
        $('#cbWorkShift').val('');
    }

    $(document).on('click', '.btnAction', function (e) {
        e.preventDefault();

        var id = $(this).data("id");
        $('#hiddenId').val(id);

        var id = $('#hiddenId').val();
        console.log('id trong hidden: ', id);
        const action = $(this).data('action');
        const buttonText = $(this).text();

        if (action === 'view') {
            console.log(`Nút được nhấn: ${buttonText}`);
            $('#title').text('Xem thông tin nhân viên');
            $('#hiddenId').val();
            $('#btnOption').hide();

            // Đặt các trường nhập liệu ở chế độ readonly
            $('#fullName1').prop('readonly', true);
            $('#phone').prop('readonly', true);
            $('#dateOfBirth').prop('readonly', true);
            $('#address').prop('readonly', true);
            $('#username').prop('readonly', true);
            $('#password').prop('readonly', true);
            $('#cbSex').prop('disabled', true);
            $('#cbWorkShift').prop('disabled', true);

        } else if (action === 'edit') {
            $('#btnOption').show();
            console.log(`Nút được nhấn: ${buttonText}`);
            $('#title').text('Cập nhật nhân viên');

            // Đặt các trường nhập liệu ở chế độ 
            $('#fullName1').prop('readonly', false);
            $('#phone').prop('readonly', false);
            $('#dateOfBirth').prop('readonly', false);
            $('#address').prop('readonly', false);
            $('#username').prop('readonly', false);
            $('#password').prop('readonly', false);
            $('#cbSex').prop('disabled', false);
            $('#cbWorkShift').prop('disabled', false);
        }


        var id = $(this).data("id");
        $('#hiddenId').val(id);
        //console.log('id = ' + id);
        var baseUrl = 'http://localhost:1122/account';
        $.ajax({
            method: 'GET',
            url: `${baseUrl}/get/${id}`,
            success: function (response) {
                // console.log("Full response:", JSON.stringify(response, null, 2));

                $('#fullName1').val(response.data.fullName);
                $('#phone').val(response.data.phone);
                $('#dateOfBirth').val(formatDate(response.data.dateOfBirth));
                $('#address').val(response.data.address);
                $('#username').val(response.data.username);
                loadComboBox(response.data.idWorkShift);

                var isSelectedMale = response.data.sex == 1 ? 'selected' : '';
                var isSelectedFemale = response.data.sex == 0 ? 'selected' : '';
                $('#cbSex').empty();
                $('#cbSex').append('<option value="1" ' + isSelectedMale + '>Nam</option>');
                $('#cbSex').append('<option value="0" ' + isSelectedFemale + '>Nữ</option>');

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
        $('#fullName1').val('');
        $('#phone').val('');
        $('#dateOfBirth').val('');
        $('#address').val('');
        $('#username').val('');
        $('#password').val('');
        $('#cbSex').val('');
        $('#cbWorkShift').val('');
    }


    // thêm và sửa 
    $('#btnSave').click(function (event) {
        event.preventDefault();

        var idAccount = $('#hiddenId').val();
        var fullName = $('#fullName1').val().trim();
        var phone = $('#phone').val().trim();
        var address = $('#address').val().trim();
        var sex = $('#cbSex').val();
        var dateOfBirth = $('#dateOfBirth').val().trim();
        var dateObject = new Date(dateOfBirth);
        var username = $('#username').val().trim();
        var password = $('#password').val();
        var workShift = $('#cbWorkShift').val();
        if (!fullName) {
            alert('Họ tên không được để trống');
            return;
        }
        if (!phone) {
            alert('Số điện thoại không được để trống !');
            return;
        }
        if (!address) {
            alert('Địa chỉ không được để trống !');
            return;
        }
        if (password == null) {
            alert('Mật khẩu không được để trống !');
            return;
        }
        if (username === null) {
            alert('Tên đăng nhập không được để trống !');
            return;
        }
        var formData = new FormData();
        formData.append("id", idAccount);
        formData.append("username", username);
        formData.append("password", password);
        formData.append("fullName", fullName);
        formData.append("phone", phone);
        formData.append("address", address);
        formData.append("sex", sex);
        formData.append("dateOfBirth", dateObject);
        formData.append("idWorkShift", workShift);


        if (!idAccount || idAccount === '') {
            $.ajax({
                method: 'POST',
                url: `http://localhost:1122/account/create`,
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
        } else if (idAccount != null) {
            $.ajax({
                method: 'PUT',
                url: `http://localhost:1122/account/update/${idAccount}`,
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    alert('Cập nhật thành công !');
                    resetForm();
                    $('#exampleModal').modal('hide');
                    location.reload();
                },
                error: function (xhr) {
                    alert('Có lỗi xảy ra khi cập nhật sản phẩm: ' + xhr.responseText);
                    console.error(xhr);
                }
            });
        }
    });


});
