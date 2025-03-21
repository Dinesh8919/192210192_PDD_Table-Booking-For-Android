<?php
$servername = "localhost";
$username = "root";
$password = "Dinesh@1234";
$database = "table_booking";

$conn = new mysqli($servername, $username, $password, $database);

if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Connection failed: " . $conn->connect_error]));
}
?>
