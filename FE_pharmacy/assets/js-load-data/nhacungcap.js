$(document).ready(function () {
    var size = 5;
    loadPage();

    $(document).on('click', '.page-link', function (e) {
        e.preventDefault();
        var page = $(this).data('page');
        if (page) {
            loadPage(page);
        }
    });

    function loadPage(page = 1) {
        var link = `http://localhost:1122/supplier/all?page=${page}&size=${size}`;
        $.ajax({
            method: "GET",
            url: link
        })
            .done(function (msg) {
                $("#categoryGetAll").empty();
                if (msg.data.data != null) {
                    var response = msg.data.data;
                    //console.log("Full response:", JSON.stringify(response, null, 2));
                    $.each(response, function (index, value) {
                        var stt = (msg.data.currentPage - 1) * msg.data.pageSize + index + 1;
                        var html = `<tr>
                            <td>${stt}</td>
                            <td>${value.name}</td>
                            <td class="long_text-v1">${value.address}</td>
                            <td>${value.phone}</td>
                            <td style="display: flex;">
                                <a data-id=${value.id} class="btn btn-primary" id="btnView" data-bs-toggle="modal" data-bs-target="#exampleModal"
                        data-bs-whatever="@mdo">Các lô thuốc</a>
                                <a data-id=${value.id} class="btn btn-warning" id="btnEdit" data-bs-toggle="modal" data-bs-target="#exampleModal"
                        data-bs-whatever="@mdo">Cập nhật</a>
                                <a data-id=${value.id} class="btn btn-danger" id="btnDelete">Xóa</a>
                            </td>
                        </tr>`;
                        $("#categoryGetAll").append(html);
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

    $(document).on('click', '#btnEdit', function (e) {
        e.preventDefault();
        $('#title').text('Cập nhật nhà cung cấp');

        var id = $(this).data("id");
        $('#hiddenId').val(id);

        //console.log('id = ' + id);
        var baseUrl = 'http://localhost:1122/supplier';
        $.ajax({
            method: 'GET',
            url: `${baseUrl}/getById/${id}`,
            success: function (response) {
                $('#name').val(response.data.name);
                $('#phone').val(response.data.phone);
                $('#address').val(response.data.address);
            },
            error: function (xhr, status, error) {
                alert('Không thể lấy thông tin danh mục');
                console.error('Error:', xhr.responseText, status, error);
            }
        });
    });

    $(document).on('click', '#btnAdd', function (e) {
        //e.preventDefault();
        resetForm();
    })
    function resetForm() {
        $('#hiddenId').val('');
        $('#phone').val('');
        $('#name').val('');
        $('#address').val('');
    }

    // Thêm hoặc cập nhật ca làm việc
    $('#btnSave').click(function (event) {
        event.preventDefault();

        var id = $('#hiddenId').val();
        //console.log('ID hiện tại: ' + id);
        var name = $('#name').val();
        var phone = $('#phone').val();
        var address = $('#address').val();
        var baseUrl = 'http://localhost:1122/supplier';

        if (!id || id.trim() === '') {
            $.ajax({
                method: 'POST',
                url: `${baseUrl}/create`,
                data: {
                    name: name,
                    phone: phone,
                    address: address
                },
                success: function (response) {
                    alert('Thêm thành công')
                    resetForm();
                    $('#exampleModal').modal('hide');
                    location.reload();
                },
                error: function (xhr, status, error) {
                    alert('Thêm thất bại')
                    console.error('Error:', xhr.responseText, status, error);
                }
            });
        } else {
            $.ajax({
                method: 'PUT',
                url: `${baseUrl}/update/${id}`,
                data: {
                    name: name,
                    phone: phone,
                    address: address
                },
                success: function (response) {
                    alert('Cập nhật thành công')
                    resetForm();
                    $('#exampleModal').modal('hide');
                    location.reload();
                },
                error: function (xhr, status, error) {
                    alert('Cập nhật thất bại')
                    console.error('Error:', xhr.responseText, status, error);
                }
            });
        }
    });


    function showToast(message, type, delay = 3000) {
        var toastElement = $('#notificationToast');
        var toastBody = toastElement.find('.toast-body');
        var toastClass = type === 'success' ? 'bg-success' : 'bg-danger';

        toastBody.text(message);
        toastElement.removeClass('bg-success bg-danger').addClass(toastClass);

        toastElement.toast({ delay: delay });

        var toast = new bootstrap.Toast(toastElement[0]);
        toast.show();
    }



    // xóa ca làm việc
    $(document).on('click', '#btnDelete', function (e) {
        e.preventDefault();
        var id = $(this).data("id");
        if (confirm("Bạn có chắc chắn muốn xóa không?")) {
            $.ajax({
                method: "DELETE",
                url: `http://localhost:1122/supplier/delete/${id}`,
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
});
