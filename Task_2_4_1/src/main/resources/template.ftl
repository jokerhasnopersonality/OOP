<!DOCTYPE html>
<html lang='en'>
<head>
	<title> Task Progress Report </title>
</head>
<body>
    <h2>${group.id}</h2>
    <#list tasks as task>
        <h3>${task.id}</h3>
        <table class="table">
            <thead>
            <tr>
                <th>Student</th>
                <th>Build</th>
                <th>Documentation</th>
                <th>Tests Passed</th>
                <th>Soft Deadline</th>
                <th>Hard Deadline</th>
                <th>Total</th>
            </tr>
            </thead>
            <tbody>
            <#list task.progress as p>
                <tr>
                    <td>${p.student!'None'}</td>
                    <#if p.build>
                        <td>Successful</td>
                    <#else>
                        <td>Failed</td>
                    </#if>
                    <#if p.docGenerated>
                        <td>Generated</td>
                    <#else>
                        <td>Missing</td>
                    </#if>
                    <#if p.testsCount??>
                        <td>${p.testsPassed}/${p.testsCount}</td>
                    <#else>
                        <td>Error</td>
                    </#if>
                    <#if p.softDeadline>
                        <td>Met</td>
                    <#else>
                        <td>Missed</td>
                    </#if>
                    <#if p.hardDeadline>
                        <td>Met</td>
                    <#else>
                        <td>Missed</td>
                    </#if>
                    <td>${p.score!'None'}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </#list>
</body>
<style type="text/css">
.table {
	width: 100%;
	border: none;
	margin-bottom: 20px;
}
.table thead th {
	padding: 10px;
	font-weight: 500;
	font-size: 16px;
	line-height: 20px;
	text-align: left;
	color: #444441;
	border-top: 2px solid #716561;
	border-bottom: 2px solid #716561;
}
.table tbody td {
	padding: 10px;
	font-size: 14px;
	line-height: 20px;
	color: #444441;
	border-top: 1px solid #716561;
}
</style>
</html>