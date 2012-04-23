<div class="appHeader" role="banner">
    <a href="http://appliedbiosystems.ru">
        <img src="${resource(dir: 'images', file: 'logo-applied-biosystems.png')}" alt=""/>
    </a>
    <shiro:isLoggedIn>
        <div class="userSection">
            <span>${user.name}</span>
        </div>

        <div class="logout">
            <g:link controller="auth" action="signOut">
                <img src="${resource(dir: 'images', file: 'sign-out.png')}" alt="${message(code: 'logout')}" title="${message(code: 'logout')}">
            </g:link>
        </div>
    </shiro:isLoggedIn>
</div>