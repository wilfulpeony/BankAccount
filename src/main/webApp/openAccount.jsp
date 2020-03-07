<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Bank Account</title>
</head>
<body>
<center>
    <h1>
        Please, input info to open new account:
    </h1>
    <form method="post" action="OpenAccount">
        <br>
        <select  name="Type" size="1">
            <option>Basic account</option>
            <option>Overdraft account</option>
        </select>
        <p></p>
        Enter Owner ID
        <input  name="ownerId" type="text"><br>
        <p></p>
        Enter desired amount
        <input  name="amount" type="text">

        <br><br>
        <input type="submit" value="Submit">
    </form>
</center>
</body>
</html>
