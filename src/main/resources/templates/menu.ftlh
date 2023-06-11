<html lang="en">
<head>
    <title>Menu</title>
</head>
<body>
<h1>Our delicious menu</h1>
<table>
    <tr>
        <th>Pizza</th>
        <th>Price (USD)</th>
    </tr>
    <#list pizzas as pizza>
        <tr>
            <td>${pizza.name}</td>
            <td>${pizza.price}</td>
        </tr>
    </#list>
</table>
</body>
</html>