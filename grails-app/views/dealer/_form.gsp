<div class="content edit">
    <g:if test="${isNew}">
        <h1 class="newEntity"><g:message code="dealer.action.create.title"/></h1>
    </g:if>
    <g:else>
        <h1>
            <span class="createDate">${message(code: 'dealer.date.of.creation', args: [formatDate(format: 'dd-MM-yyyy HH:mm', date: dealer.dateCreated)])}</span>
            <g:message code="dealer.action.edit.title"/>
        </h1>
    </g:else>

    <table class="entityEdit">
        <colgroup>
            <col width="30%">
            <col width="70%">
        </colgroup>

        <tr>
            <td><g:message code="dealer.name"/></td>
            <td>
                <g:textField name="name" value="${dealer.name}" class="${hasErrors(bean: dealer, field: 'name', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${dealer}" field="name"/>
            </td>
        </tr>
    </table>

</div>
