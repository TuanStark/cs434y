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
        var link = `http://localhost:1122/type/all?page=${page}&size=${size}`;
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
                            <td class="long_text-v2">${value.description}</td>
                            <td>
                                <a class="btn btn-warning" data-id="${value.id}" id="btnEdit" data-action="view" data-bs-toggle="modal" data-bs-target="#exampleModal"
                        data-bs-whatever="@mdo">Chỉnh Sửa</a>
                                <a class="btn btn-danger" data-id="${value.id}" id="btnDelete" >Xóa</a>
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

    $(document).on('click', '#btnEdit', function (e) {
        e.preventDefault();
        $('#title').text('Cập nhật loại thuốc');

        var id = $(this).data("id");
        $('#hiddenId').val(id);

        //console.log('id = ' + id);
        var baseUrl = 'http://localhost:1122/type';
        $.ajax({
            method: 'GET',
            url: `${baseUrl}/getById/${id}`,
            success: function (response) {
                $('#nameType').val(response.data.name);
                $('#description').val(response.data.description);
            },
            error: function (xhr, status, error) {
                alert('Không thể lấy thông tin danh mục');
                console.error('Error:', xhr.responseText, status, error);
            }
        });
    });

    // Thêm hoặc cập nhật 
    $('#btnSave').click(function (event) {
        event.preventDefault();

        var id = $('#hiddenId').val();
        // var idLong = BigInt(id);
        console.log('id = ' + id);
        var name = $('#nameType').val();
        var description = $('#description').val();
        var baseUrl = 'http://localhost:1122/type';

        if (!name || name.trim() === '') {
            alert('Vui lòng nhập tên loại thuốc.');
            return;
        }

        if (!description || description.trim() === '') {
            alert('Vui lòng nhập mô tả.');
            return;
        }

        if (id === '') {
            $.ajax({
                method: 'POST',
                url: `${baseUrl}/create`,
                data: { name, description },
                success: function (response) {
                    alert('Thêm thành công');
                    resetForm();
                    $('#exampleModal').modal('hide');
                    location.reload();
                },
                error: function (xhr, status, error) {
                    alert('Thêm thất bại');
                    console.error('Error:', xhr.responseText, status, error);
                }
            });
        } else {
            $.ajax({
                method: 'PUT',
                url: `${baseUrl}/update/${id}`,
                data: { name, description },
                success: function (response) {
                    alert('Cập nhật thành công');
                    resetForm();
                    $('#exampleModal').modal('hide');
                    location.reload();
                },
                error: function (xhr, status, error) {
                    alert('Cập nhật thất bại');
                    console.error('Error:', xhr.responseText, status, error);
                }
            });
        }
    });


    function resetForm() {
        $('#hiddenId').val('');
        $('#nameType').val('');
        $('#description').val('');
    }

    // xóa 
    $(document).on('click', '#btnDelete', function (e) {
        e.preventDefault();
        var id = $(this).data("id");
        if (confirm("Bạn có chắc chắn muốn xóa không?")) {
            $.ajax({
                method: "DELETE",
                url: `http://localhost:1122/type/delete/${id}`,
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
