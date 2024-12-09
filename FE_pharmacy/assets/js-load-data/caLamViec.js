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
        var link = `http://localhost:1122/ws/all?page=${page}&size=${size}`;
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
                            <td>${value.name}</td>
                            <td>${value.time}</td>
                            <td>
                                <a data-id="${value.id}" id="btnEdit" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#exampleModal"
                        data-bs-whatever="@mdo">Chỉnh Sửa</a>
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

    $(document).on('click', '#btnAdd', function (e) {
        //e.preventDefault();
        resetForm();
    })
    function resetForm() {
        $('#hiddenId').val('');
        $('#nameWorkShift').val('');
        $('#time').val('');

    }

    $(document).on('click', '#btnEdit', function (e) {
        e.preventDefault();
        $('#title').text('Cập nhật ca làm việc');
        var id = $(this).data("id");
        $('#hiddenId').val(id);
        //console.log('id = ' + id);
        var baseUrl = 'http://localhost:1122/ws';
        $.ajax({
            method: 'GET',
            url: `${baseUrl}/getById/${id}`,
            success: function (response) {
                $('#nameWorkShift').val(response.data.name);
                $('#time').val(response.data.time);
            },
            error: function (xhr, status, error) {
                alert('Không thể lấy thông tin danh mục');
                console.error('Error:', xhr.responseText, status, error);
            }
        });
    });

    // Thêm hoặc cập nhật ca làm việc
    $('#btnSave').click(function (event) {
        event.preventDefault();

        var id = $('#hiddenId').val();
        //console.log('ID hiện tại: ' + id);
        var name = $('#nameWorkShift').val();
        var time = $('#time').val();
        var baseUrl = 'http://localhost:1122/ws';

        if (!id || id.trim() === '') {
            $.ajax({
                method: 'POST',
                url: `${baseUrl}/create`,
                data: { name: name, time: time },
                success: function (response) {
                    // showToast('Thêm thành công!', 'success');
                    alert('Thêm thành công')
                    resetForm();
                    $('#exampleModal').modal('hide');
                    location.reload();
                },
                error: function (xhr, status, error) {
                    alert('Thêm thất bại')
                    // showToast('Lỗi khi thêm ', 'error');
                    console.error('Error:', xhr.responseText, status, error);
                }
            });
        } else {
            $.ajax({
                method: 'PUT',
                url: `${baseUrl}/update/${id}`,
                data: { name: name, time: time },
                success: function (response) {
                    // showToast('Cập nhật thành công!', 'success', 3000);
                    alert('Cập nhật thành công')
                    resetForm();
                    $('#exampleModal').modal('hide');
                    location.reload();
                },
                error: function (xhr, status, error) {
                    // showToast('Lỗi khi cập nhật ', 'error');
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



    function resetForm() {
        $('#hiddenId').val('');
        $('#nameWorkShift').val('');
        $('#time').val('');
    }

    // xóa ca làm việc
    $(document).on('click', '#btnDelete', function (e) {
        e.preventDefault();
        var id = $(this).data("id");
        if (confirm("Bạn có chắc chắn muốn xóa không?")) {
            $.ajax({
                method: "DELETE",
                url: `http://localhost:1122/ws/delete/${id}`,
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
