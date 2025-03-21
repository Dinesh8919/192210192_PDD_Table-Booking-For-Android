<?php
header("Content-Type: application/json");
require 'db.php'; // Database connection file

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $table_no = $_POST['table_no'] ?? null;
    $table_name = $_POST['table_name'] ?? null;
    $table_description = $_POST['table_description'] ?? null;
    $table_capacity = $_POST['table_capacity'] ?? null;
    $table_status = $_POST['table_status'] ?? 'available'; // Default status

    // Validate required fields
    if (!$table_no || !$table_name || !$table_description || !$table_capacity) {
        http_response_code(400);
        echo json_encode(["status" => 400, "message" => "Missing required fields $table_capacity"]);
        exit;
    }

    // Validate table number is an integer
    // if (!filter_var($table_no, FILTER_VALIDATE_INT)) {
    //     http_response_code(405);
    //     echo json_encode(["status" => 405, "message" => "Table number must be an integer"]);
    //     exit;
    // }

    // Handle Image Upload
    $upload_dir = "uploads/";
    $table_image = null;

    if (isset($_FILES['table_image']) && $_FILES['table_image']['error'] == 0) {
        $image_name = time() . "_" . basename($_FILES["table_image"]["name"]); // Unique filename
        $target_path = $upload_dir . $image_name;

        // Validate image type
        $allowed_types = ['image/jpeg', 'image/png', 'image/jpg'];
        if (!in_array($_FILES["table_image"]["type"], $allowed_types)) {
            http_response_code(404);
            echo json_encode(["status" => 404, "message" => "Invalid image format"]);
            exit;
        }

        // Move image to uploads folder
        if (move_uploaded_file($_FILES["table_image"]["tmp_name"], $target_path)) {
            $table_image = $image_name;
        } else {
            http_response_code(402);
            echo json_encode(["status" => 402, "message" => "Failed to upload image"]);
            exit;
        }
    } else {
        http_response_code(423);
        echo json_encode(["status" => 423, "message" => "Image file is required"]);
        exit;
    }

    // Insert into database
    $stmt = $conn->prepare("INSERT INTO restaurant (table_no, table_name, table_description, table_images, table_status, table_capacity) VALUES (?, ?, ?, ?,?, ?)");
    $stmt->bind_param("sssssi", $table_no, $table_name, $table_description, $table_image, $table_status, $table_capacity);

    try {

        if ($stmt->execute()) {
            http_response_code(200);
            echo json_encode(["status" => 200, "message" => "Table added successfully", "image" => $table_image]);
        } else {
            http_response_code(420);
            echo json_encode(["status" => 420, "message" => "Database error: " . $conn->error]);
        }
    

    } catch(Exception $e) {
        http_response_code(500);
        echo json_encode(["status" => 500, "message" => "" . $e->getMessage()]);
    }

    $stmt->close();
    $conn->close();
} else {
    http_response_code(422);
    echo json_encode(["status" => 422, "message" => "Invalid request method"]);
}
?>
