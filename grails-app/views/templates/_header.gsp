<div class="appHeader" role="banner">
    <a href="http://appliedbiosystems.ru"><img src="${resource(dir: 'images', file: 'logo-applied-biosystems.png')}" alt=""/></a>
    <shiro:isLoggedIn>
        <div class="userSection">
            <span class="userData">
                <g:link controller="settings" action="showSettings" id="${user.id}">${user.name}</g:link>&nbsp;
                <span>(${message(code: 'ROLE.' + user.role.name)})</span>
            </span>
        </div>

        <div class="logout">
            <g:link controller="auth" action="signOut"><img src="${resource(dir: 'images', file: 'sign-out.png')}" alt="${message(code: 'logout')}" title="${message(code: 'logout')}"></g:link>
        </div>
    </shiro:isLoggedIn>

    <span class="flashMessage" style="display: ${flash.message ? '' : 'none'};">
        ${flash.message}
        <a href="#" class="closeFlash"><img class="" src="${resource(dir: 'images', file: 'remove_button.png')}" alt="${message(code: 'project.document.upload.link')}"/></a>
    </span>
</div>