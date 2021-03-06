<div class="appHeader" role="banner">
    <a href="http://appliedbiosystems.ru"><img src="${resource(dir: 'images', file: 'logo-applied-biosystems.png')}" alt=""/></a>
    <shiro:user>
        <div class="userSection">
            <span class="userData">
                <g:link controller="settings" action="showSettings" id="${user.id}">${user.name}</g:link>&nbsp;
                <span>(${message(code: 'ROLE.' + user.role.name)}<shiro:hasRole name="${ShiroRole.ROLE_DEALER}">,&nbsp;${user.dealer.name}</shiro:hasRole>)</span>
            </span>
        </div>

        <div class="logout">
            <g:link controller="auth" action="signOut"><img src="${resource(dir: 'images', file: 'sign-out.png')}" alt="${message(code: 'logout')}" title="${message(code: 'logout')}"></g:link>
        </div>
    </shiro:user>

    <span class="flashMessage" style="display: ${flash.message ? '' : 'none'};">
        <table>
            <colgroup>
                <col width="95%">
                <col width="5%">
            </colgroup>
            <tr>
                <td>${flash.message}</td>
                <td align="right">
                    <a href="#" class="closeFlash"><img class="" src="${resource(dir: 'images', file: 'remove_button.png')}" alt="${message(code: 'close')}"/></a>
                </td>
            </tr>
        </table>
    </span>
</div>