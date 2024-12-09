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
    function formatCurrency(value) {
        if (isNaN(value)) {
            return '';
        }
        return value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
    }
    $(document).on('click', '.page-link', function (e) {
        e.preventDefault();
        var page = $(this).data('page');
        if (page) {
            loadPage(page);
        }
    });

    function loadPage(page = 1) {
        var link = `http://localhost:1122/invoice/all?page=${page}&size=${size}`;
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
                            <td>${value.codeInvoice}</td>
                            <td>${value.saleInvoiceID.nameCustomer == null ? '<i class="fas fa-ellipsis-h text-danger">' : value.saleInvoiceID.nameCustomer}</td>
                            <td>${formatDate(value.dateInvoice)}</td>
                            <td>${value.saleInvoiceID.examinationCode == null ? '<i class="fas fa-ellipsis-h text-danger">' : value.saleInvoiceID.examinationCode}</td>
                            <td>${value.saleInvoiceID.examinationPlace == null ? '<i class="fas fa-ellipsis-h text-danger">' : value.saleInvoiceID.examinationPlace}</td>
                           
                            <td>
                                <a id="btnView" data-id=${value.id} data-bs-toggle="modal"
                        data-bs-target="#exampleModal" data-bs-whatever="@mdo" class="btn btn-primary">Xem Chi Tiết</a>
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

    $(document).on('click', '#btnView', function (e) {
        var id = $(this).data("id");
        console.log(id);
        $('#btnOption').hide();
        $.ajax({
            url: `http://localhost:1122/invoice/getById/${id}`,
            method: 'GET',
            success: function (response) {
                console.log("Full response:", JSON.stringify(response, null, 2));
                $('#nameCustomer').text(response.data.saleInvoiceID.nameCustomer);
                $('#phone').text(response.data.saleInvoiceID.phone);
                $('#nameAccount').text(response.data.nameAccount);
                $('#invoiceDate').text(formatDate(response.data.dateInvoice));
                $('#typeInvoice').text(
                    response.data.typeInvoice == 1 ? "Hóa đơn nhập" :
                        response.data.typeInvoice == 2 ? "Hóa đơn bán" :
                            response.data.typeInvoice == 3 ? "Hóa đơn thanh lí" :
                                "Hóa đơn khác"
                );
                $('#totalPrice1').text(formatCurrency(response.data.totalMoney));
                $('#invoiceCode').text(response.data.codeInvoice);
                $('#location').text(response.data.saleInvoiceID.examinationPlace);
                $('#examCode').text(response.data.saleInvoiceID.examinationCode);
                $("#getAllDetail").empty();
                $.each(response.data.invoiceDetailRequestList, function (index, value) {
                    var html = `<tr>
                        <td>${value.codeMedicine}</td>
                        <td>${value.nameMedicine}</td>
                        <td>${(value.prescriptionMedicine) == 1 ? 'Thuốc kê đơn' : 'Thuốc không kê đơn'}</td>
                        <td>${value.unitOfCalculator}</td>
                        <td>${value.quantity}</td>
                        <td>${formatCurrency(value.totalMoney)}</td>
                    </tr>`;
                    $("#getAllDetail").append(html);
                });

            },
            error: function () {
                alert('Lỗi');
            }
        })
    })
})