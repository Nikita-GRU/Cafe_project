package by.gruca.cafe.util;

import javax.servlet.http.HttpServletRequest;

import static by.gruca.cafe.service.impl.OrderServiceImpl.CONVERTER;

/**
 * The type Paginator.
 */
public class Paginator {
    /**
     * The constant DEFAULT_ITEMS_PER_PAGE.
     */
    public static final int DEFAULT_ITEMS_PER_PAGE = 10;
    /**
     * The constant ITEMS_PER_PAGE_ATTRIBUTE.
     */
    public static final String ITEMS_PER_PAGE_ATTRIBUTE = "items_per_page";
    /**
     * The constant PAGE_NUMBER_ATTRIBUTE.
     */
    public static final String PAGE_NUMBER_ATTRIBUTE = "page_number";
    /**
     * The constant FIRST_PAGE_NUMBER.
     */
    public static final int FIRST_PAGE_NUMBER = 1;
    /**
     * The constant PAGES_COUNT_ATTRIBUTE.
     */
    public static final String PAGES_COUNT_ATTRIBUTE = "pages_count";
    private final int itemsCount;
    private final int pageNumber;
    private final int itemsPerPage;
    private final int pagesCount;

    /**
     * Instantiates a new Paginator.
     *
     * @param req        the req
     * @param itemsCount the items count
     * @throws UtilException the util exception
     */
    public Paginator(HttpServletRequest req, int itemsCount) throws UtilException {
        this.itemsCount = itemsCount;
        String itemsPerPageParam = req.getParameter(ITEMS_PER_PAGE_ATTRIBUTE);
        String pageNumberParam = req.getParameter(PAGE_NUMBER_ATTRIBUTE);
        updateItemsPerPageAttribute(req, itemsPerPageParam);
        this.itemsPerPage = getItemsPerPageFromSession(req);
        this.pageNumber = pageNumberParam == null ? FIRST_PAGE_NUMBER : CONVERTER.valueOfPositiveInteger(pageNumberParam);
        this.pagesCount = itemsCount % this.itemsPerPage == 0 ? itemsCount / this.itemsPerPage : itemsCount / this.itemsPerPage + 1;
        req.setAttribute(PAGES_COUNT_ATTRIBUTE, pagesCount);
    }

    private Integer getItemsPerPageFromSession(HttpServletRequest req) throws UtilException {
        if (req.getSession().getAttribute(ITEMS_PER_PAGE_ATTRIBUTE) == null) {
            req.getSession().setAttribute(ITEMS_PER_PAGE_ATTRIBUTE, DEFAULT_ITEMS_PER_PAGE);
        }

        return CONVERTER.valueOfInteger(req.getSession().getAttribute(ITEMS_PER_PAGE_ATTRIBUTE).toString());
    }

    private void updateItemsPerPageAttribute(HttpServletRequest req, String itemsPerPageParam) throws UtilException {
        if (!(itemsPerPageParam == null || itemsPerPageParam.isEmpty())) {
            req.getSession().setAttribute(ITEMS_PER_PAGE_ATTRIBUTE, CONVERTER.valueOfPositiveInteger(itemsPerPageParam));
        }
    }

    /**
     * Gets items count.
     *
     * @return the items count
     */
    public int getItemsCount() {
        return itemsCount;
    }

    /**
     * Gets page number.
     *
     * @return the page number
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Gets items per page.
     *
     * @return the items per page
     */
    public int getItemsPerPage() {
        return itemsPerPage;
    }

    /**
     * Gets pages count.
     *
     * @return the pages count
     */
    public int getPagesCount() {
        return pagesCount;
    }
}
