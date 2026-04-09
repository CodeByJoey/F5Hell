function movePage(page) {
    document.querySelector('input[name="page"]').value = page;
    document.getElementById("searchForm").submit();
}
