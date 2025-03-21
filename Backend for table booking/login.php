<?php
session_start();
require 'db.php'; // Database Connection

header("Content-Type: application/json");

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (!isset($_POST['email']) || !isset($_POST['password'])) {
        http_response_code(400);
        echo json_encode(["status" => 400, "message" => "Missing Email or Password"]);
        exit;
    }

    $email = trim($_POST['email']);
    $password = trim($_POST['password']);

    // Debug Email
    // echo "Email: " . $email; 
    // exit;

    // Prepare SQL Query
    $stmt = $conn->prepare("SELECT id, name, email, password,usertype FROM users WHERE email = ?");
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {
        $stmt->bind_result($id, $name, $db_email, $db_password,$usertype);
        $stmt->fetch();

        if ($password === $db_password) {
            $_SESSION['user_id'] = $id;
            $_SESSION['user_name'] = $name;
            $_SESSION['user_email'] = $db_email;
            $_SESSION['usertypr'] = $usertype;

            $user = [
                "id" => $id,
                "name" => $name,
                "email" => $db_email,
                "usertype"=>$usertype
            ];

            http_response_code(200);
            echo json_encode(["status" => 200, "message" => "Login Successful!", "user" => $user]);
        } else {
            http_response_code(401);
            echo json_encode(["status" => 401, "message" => "Invalid Password!"]);
        }
    } else {
        http_response_code(404);
        echo json_encode(["status" => 404, "message" => "User Not Found!"]);
    }

    $stmt->close();
} else {
    http_response_code(405);
    echo json_encode(["status" => 405, "message" => $_SERVER["REQUEST_METHOD"] . " Method Not Allowed"]);
}

$conn->close();
?>
