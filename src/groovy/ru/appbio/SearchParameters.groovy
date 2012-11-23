package ru.appbio
/**
 * Base class for search parameters
 */
class SearchParameters {

    /** First record to show    */
    def offset

    /** How many records to show   */
    def max

    /** Sort property name    */
    def sort

    /** Sort order    */
    def order

    /** Quick search tokens  */
    def quickSearch = []

    /** Returns map of sorting and pagination parameters    */
    def getLimits() {
        [offset: offset, max: max, sort: sort, order: order]
    }

    /** Binds sorting and pagination parameters from request parameters   */
    def bindLimits(params) {
        max = params.int("max")
        offset = params.int("offset")
        sort = params.sort
        order = params.order
        if (params.q) {
            String q = params.q.trim()
            for (token in q.split(" ")) {
                if (token.trim().length() > 0) {
                    quickSearch << token.trim()
                }
            }
        }
    }
}
