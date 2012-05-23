<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2">
    <title><g:message code="project.action.show.title"/></title>
    <script src="${resource(dir: "js", file: "projects.js")}" type="text/javascript"></script>
</head>

<body>
<div class="actionMenu">
    <ul>
        <li>
            <g:link controller="project" class="delete deleteProjectLink" action="delete" id="${project.id}" helpertext="${message(code: 'project.delete.confirm')}">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'delete.png')}"
                     alt="${message(code: 'project.action.delete')}"/><g:message code="project.action.delete"/>
            </g:link>
        </li>

        <li>
            <g:link controller="project" action="edit" id="${project.id}">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'edit.png')}"
                     alt="${message(code: 'project.action.edit')}"/><g:message code="project.action.edit"/>
            </g:link>
        </li>

        <li>
            <g:link controller="project" action="create">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'add.png')}"
                     alt="${message(code: 'project.action.add')}"/><g:message code="project.action.add"/>
            </g:link>
        </li>

        <li class="actionSeparator">
            &nbsp;
        </li>

        <shiro:hasRole name="${ShiroRole.ROLE_ADMIN}">
            <g:if test="${project.approvalStatus == ru.appbio.LTProjectStatus.NEW}">
                <li>
                    <g:link controller="project" action="reject" id="${project.id}">
                        <img class="actionIcon" src="${resource(dir: 'images', file: 'reject.png')}"
                             alt="${message(code: 'project.reject')}"/><g:message code="project.reject"/>
                    </g:link>
                </li>

                <li>
                    <g:link controller="project" action="approve" id="${project.id}">
                        <img class="actionIcon" src="${resource(dir: 'images', file: 'approve.png')}"
                             alt="${message(code: 'project.approve')}"/><g:message code="project.approve"/>
                    </g:link>
                </li>
            </g:if>
        </shiro:hasRole>

    </ul>

</div>

<div class="content show">
    <h1>
        <span class="createDate">${message(code: 'project.date.of.creation', args: [formatPlainDate(value: project.createDate)])}</span>
        ${project.name},&nbsp;<span class="projectStatus st${project.approvalStatus.id}"><g:message
            code="${'project.status.lt.' + project.approvalStatus.id}"/></span>
    </h1>

    <table class="entityShow">
        <colgroup>
            <col width="35%">
            <col width="65%">
        </colgroup>
        <tr>
            <td><g:message code="project.productName"/></td>
            <td>${project.productName}</td>
        </tr>
        <tr>
            <td><g:message code="project.dealer"/></td>
            <td>${project.dealer?.name}</td>
        </tr>
        <tr>
            <td><g:message code="project.status"/></td>
            <td><g:message code="${'project.status.' + project.status.id}"/></td>
        </tr>
        <tr>
            <td><g:message code="project.customer"/></td>
            <td>${project.customer}</td>
        </tr>
        <tr>
            <td><g:message code="project.customerName"/></td>
            <td>${project.customerName}</td>
        </tr>
        <tr>
            <td><g:message code="project.customer.inn"/></td>
            <td>${project.inn}</td>
        </tr>
        <tr>
            <td><g:message code="project.department"/></td>
            <td>${project.department}</td>
        </tr>
        <tr>
            <td><g:message code="project.city"/></td>
            <td>${project.city?.name}</td>
        </tr>
        <tr>
            <td><g:message code="project.contactPerson"/></td>
            <td>${project.contactPerson}</td>
        </tr>
        <tr>
            <td><g:message code="project.contactEmail"/></td>
            <td>${project.contactEmail}</td>
        </tr>

        <tr>
            <td><g:message code="project.contactPhone"/></td>
            <td>${project.contactPhone}</td>
        </tr>

        <tr>
            <td><g:message code="project.releaseDate"/></td>
            <td><g:formatPlainDate value="${project.releaseDate}"/></td>
        </tr>

        <tr>
            <td><g:message code="project.closeDate"/></td>
            <td><g:formatPlainDate value="${project.closeDate}"/></td>
        </tr>

        <tr>
            <td><g:message code="project.sum"/></td>
            <td><g:formatMoney value="${project.sum}"/></td>
        </tr>

        <tr>
            <td><g:message code="project.comments"/></td>
            <td>${project.comments}</td>
        </tr>
    </table>

    <h1>
        <g:message code="comments.header"/>
        <span class="commentAddBlock"><a class="addCommment" href=#>
            <img class="commentIcon" src="${resource(dir: 'images', file: 'add_comment.png')}" alt="${message(code: 'comments.add')}"/>${message(code: 'comments.add')}</a>
        </span>
    </h1>
    <div class="CommentForm CommentTemplate" style="display: none;">
        <g:form controller="project" action="addComment" method="POST">
            <g:hiddenField name="projectId" value="${project.id}"/>
            <g:textArea name="text" rows="4" cols="50"/>
            <br/>
            <g:submitButton name="${message(code: 'comments.add')}"/>
            <a href="#" class="cancelComment"><g:message code="cancel"/></a>
        </g:form>
    </div>
    <g:if test="${project.userComments?.size() == 0 }">
        <p class="noComments">
            <g:message code="no.comments"/>
            <a class="addCommment" href=#>
                ${message(code: 'comments.add')}
            </a>
        </p>
    </g:if>
    <g:else>
        <div id="commentsList">
            <g:each in="${project.userComments}" var="nextComment">
                <p>
                    <span class="commentTitle"><g:formatPlainDate value="${nextComment.createDate}"/>&nbsp;
                        <g:if test="${nextComment.author == currentUser}">
                            <g:link controller="settings" action="showSettings">${nextComment.author.name}</g:link>
                        </g:if>
                        <g:else>
                            <shiro:hasRole name="${ShiroRole.ROLE_ADMIN}">
                                <g:link controller="user" action="show" id="${nextComment.author.id}">${nextComment.author.name}</g:link>
                            </shiro:hasRole>
                            <shiro:hasRole name="${ShiroRole.ROLE_DEALER}">
                                ${nextComment.author.name}
                            </shiro:hasRole>
                        </g:else>
                        (${message(code: 'ROLE.' + nextComment.author.role.name)})
                        <g:if test="${nextComment.author == currentUser}">
                            <g:link controller="project" class="deleteCommentLink" action="deleteComment" id="${nextComment.id}" params="${[projectId: project.id]}" helpertext="${message(code: 'comment.delete.confirmation')}"><g:message code="delete"/></g:link>
                        </g:if>
                    </span>
                    <span class="commentBody">
                        ${nextComment.text}
                    </span>
                </p>
            </g:each>
        </div>
    </g:else>
</div>
</body>
</html>
