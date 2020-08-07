var footer = $('.footer'),
    pageContainer = $('.page-container'),
    fixClass = 'navbar-fixed-bottom';

function stickyFooter() {
    var windowHeight = $(window).height(),
        contentHeight = pageContainer.height(),
        footerHeight = footer.height();
    footer.removeClass(fixClass);
    if (contentHeight <= windowHeight - footerHeight) {
        footer.addClass(fixClass);
    }
}

stickyFooter();
$(window).resize(function () {
    stickyFooter();
});