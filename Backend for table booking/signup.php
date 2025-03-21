<?php
session_start();
require 'db.php'; // Including the database connection file

if ($_SERVER["REQUEST_METHOD"] == "POST") {

    if(!isset($_POST['name']) || !isset($_POST['email']) || !isset($_POST['password']) 
    || !isset($_POST['phone'])) {
        http_response_code(400);
        echo json_encode(["status" => 400, "message" => "Missing Body"] );
        exit;
        
    }
    $name = trim($_POST['name']);
    $email = trim($_POST['email']);
    $password = trim($_POST['password']);
    $phone = trim($_POST['phone']);
    // $hashed_password = password_hash($password, PASSWORD_DEFAULT);

    // Check if email or phone number already exists
    $stmt = $conn->prepare("SELECT id FROM users WHERE email = ? OR phone = ?");
    $stmt->bind_param("ss", $email, $phone);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {
        http_response_code(409);
        echo json_encode(["status" => 409, "message" => "Email or Phone Number already registered!"]);
    } else {
        // Insert new user
        $stmt = $conn->prepare("INSERT INTO users (name, email, password, phone, usertype) VALUES (?, ?, ?, ?, ?)");
        $usertype  = 100;
        $stmt->bind_param("ssssi", $name, $email, $password, $phone, $usertype);

        if ($stmt->execute()) {
            http_response_code(200);
            echo json_encode(["status" => 200, "message" => "Signup successful!"]);
        } else {
            http_response_code(500);
            echo json_encode(["status" => 500, "message" => "Error: " . $conn->error]);
        }
    }
    $stmt->close();
} else  {
    http_response_code(400);
    echo json_encode(["status" => 400, "message" => $_SERVER["REQUEST_METHOD"] . " Method Not Allowed "] );
}
$conn->close();
?>
