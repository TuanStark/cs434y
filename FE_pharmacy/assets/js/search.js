document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("searchInput");
    const suggestionList = document.getElementById("suggestionList");

    // Hiển thị gợi ý khi nhấn vào ô tìm kiếm
    searchInput.addEventListener("focus", function () {
        suggestionList.style.display = "block";
    });

    // Ẩn gợi ý khi nhấp ra ngoài
    document.addEventListener("click", function (e) {
        if (!searchInput.contains(e.target) && !suggestionList.contains(e.target)) {
            suggestionList.style.display = "none";
        }
    });

    // Ẩn gợi ý khi nhấn phím Esc
    searchInput.addEventListener("keydown", function (e) {
        if (e.key === "Escape") {
            suggestionList.style.display = "none";
        }
    });
});
