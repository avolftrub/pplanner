import ru.appbio.SearchParameters

class DealerController {

    def userService

    def dealerService

    def show = {
        def dealer = Dealer.get(params.id)
        if (!dealer) {
            redirect(controller: 'dealer', action: 'list')
        }

        [dealer: dealer]
    }

    def edit = {
        def dealer = Dealer.get(params.id)
        if (!dealer) {
            redirect(controller: 'dealer', action: 'list')
        }

        [dealer: dealer, isNew: false]
    }

    def list = {
        def filter = prepareFilter()
        def results = dealerService.findDealers(filter)
        [dealers: results.list, total: results.totalCount, filter: filter]
    }


    def update = {
        def dealer = Dealer.get(params.id)
        if (!dealer) {
            redirect(controller: 'dealer', action: 'list')
        }

        dealer.properties = params

        dealer.validate()

        if (!dealer.hasErrors() && dealer.save()) {
            redirect(controller: 'dealer', action: 'show')
        } else {
            render(view: '/dealer/edit', model: [dealer: dealer])
        }
    }

    def save = {
        def dealer = new Dealer()

        dealer.properties = params

        dealer.validate()

        if (!dealer.hasErrors() && dealer.save()) {
            redirect(controller: 'dealer', action: 'show')
        } else {
            render(view: '/dealer/create', model: [dealer: dealer, isNew: true, currentUser: userService.getCurrentUser()])
        }
    }

    def delete = {
        def dealer = Dealer.get(params.id)
        if (!dealer) {
            redirect(controller: 'dealer', action: 'list')
        }

        dealer.delete()

        redirect(controller: 'dealer', action: 'list')
    }

    def create = {
        [dealer: new Dealer(), isNew: true, currentUser: userService.getCurrentUser()]
    }

    private def prepareFilter() {
        if (!params."sort") {
            params.sort = "name"
            params.order = "asc"

        }

        def filter = new SearchParameters()

        bindData(filter, params)
        params.max = Math.min(params.int('max') ?: 20, 100)
        filter.bindLimits(params)
        return filter
    }
}
