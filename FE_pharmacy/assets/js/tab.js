// scripts.js
document.addEventListener("DOMContentLoaded", () => {
    const tabButtons = document.querySelectorAll(".tab-button");
    const tabContents = document.querySelectorAll(".tab-content");

    tabButtons.forEach(button => {
        button.addEventListener("click", () => {
            const targetTab = button.getAttribute("data-tab");

            // Loại bỏ lớp "active" khỏi tất cả các nút và nội dung
            tabButtons.forEach(btn => btn.classList.remove("active"));
            tabContents.forEach(content => content.classList.remove("active"));

            // Thêm lớp "active" vào nút và nội dung được chọn
            button.classList.add("active");
            document.getElementById(targetTab).classList.add("active");
        });
    });
});
