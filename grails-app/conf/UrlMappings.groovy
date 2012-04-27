class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        "/" {
            controller = "project"
            action = "list"
        }
        "404"(view:'/404')
		"500"(view:'/error')
	}
}
