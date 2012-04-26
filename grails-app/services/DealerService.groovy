import ru.appbio.SearchParameters

class DealerService {

/** Finds dealers with the speicified parameters             */
    def findDealers(SearchParameters filter) {
        Dealer.createCriteria().list(filter.getLimits()) {

            //quick search
            if (filter.quickSearch) {
                for (token in filter.quickSearch) {
                    like('name', token)
                }
            }
        }
    }
}
