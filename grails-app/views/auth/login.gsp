<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="layout" content="main2" />
  <title>Login</title>
</head>
<body>
  <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
  </g:if>
  <g:form action="signIn">
    <input type="hidden" name="targetUri" value="${targetUri}" />
    <table class="loginTable">
        <colgroup>
            <col width="20%">
            <col width="80%">
        </colgroup>
       <tbody>
        <tr>
          <td><g:message code="login.username"/>:</td>
          <td><input type="text" name="username" value="${username}" /></td>
        </tr>
        <tr>
          <td><g:message code="login.password"/>:</td>
          <td><input type="password" name="password" value="" /></td>
        </tr>
        <tr>
          <td></td>
          <td><g:checkBox class="rememberme" id="remme" name="rememberMe" value="${rememberMe}" />
              <label for="remme"><g:message code="login.remember"/></label>
          </td>
        </tr>
        <tr>
          <td></td>
            <td>
                <button class="Login" type="submit">
                    <span><g:message code="login"/></span>
                </button>
            </td>
            %{--<td><input type="submit" value="Sign in" /></td>--}%
        </tr>
      </tbody>
    </table>
  </g:form>
</body>
</html>
