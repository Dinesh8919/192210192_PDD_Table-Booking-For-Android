<?php

header('Content-Type: application/json');
require 'db.php'; // Include your database connection file

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $user_id = $_POST['user_id'] ?? '';
    $table_no = $_POST['table_no'] ?? '';
    $starting_time = $_POST['starting_time'] ?? '';
    $ending_time = $_POST['ending_time'] ?? '';
    $booking_date = $_POST['booking_date'] ?? '';
    $amount = $_POST['amount'] ?? '';
    $transaction_id = $_POST['transaction_id'] ?? '';

    // Validation
    if (empty($user_id) || empty($table_no) || empty($starting_time) || empty($ending_time)
     || empty($booking_date) || empty($amount) || empty($transaction_id)) {
        http_response_code(400);
        echo json_encode(['message' => 'All fields are required.']);
        exit;
    }

    // Validate date format (YYYY-MM-DD)
    if (!preg_match('/^\d{4}-\d{2}-\d{2}$/', $booking_date)) {
        http_response_code(400);
        echo json_encode(['message' => 'Invalid date format. Use YYYY-MM-DD.']);
        exit;
    }

    // Validate time format (HH:MM:SS)
    if (!preg_match('/^\d{2}:\d{2}:\d{2}$/', $starting_time) || 
        !preg_match('/^\d{2}:\d{2}:\d{2}$/', $ending_time)) {
        http_response_code(400);
        echo json_encode(['message' => 'Invalid time format. Use HH:MM:SS.']);
        exit;
    }

    // Ensure starting time is before ending time
    if (strtotime($starting_time) >= strtotime($ending_time)) {
        http_response_code(400);
        echo json_encode(['message' => 'Starting time must be before ending time.']);
        exit;
    }

    // Check if the table is already booked for the given time range
    $stmt = $conn->prepare("SELECT COUNT(*) FROM tbl_bookings 
                            WHERE table_no = ? AND booking_date = ? 
                            AND ((starting_time < ? AND ending_time > ?) 
                            OR (starting_time < ? AND ending_time > ?) 
                            OR (starting_time >= ? AND ending_time <= ?))");
    $stmt->bind_param("ssssssss", $table_no, $booking_date, 
                      $ending_time, $starting_time, 
                      $starting_time, $ending_time, 
                      $starting_time, $ending_time);
    $stmt->execute();
    $stmt->bind_result($count);
    $stmt->fetch();
    $stmt->close();

    if ($count > 0) {
        http_response_code(400);
        echo json_encode(['message' => 'Table is already booked for the selected time.']);
        exit;
    }

    // Insert data into database
    $stmt = $conn->prepare("INSERT INTO tbl_bookings (user_id, table_no, starting_time, ending_time, booking_date, amount, transaction_id) 
                            VALUES (?, ?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("iisssds", $user_id, $table_no, $starting_time, $ending_time, $booking_date, $amount, $transaction_id);

    if ($stmt->execute()) {
        http_response_code(200);
        echo json_encode(['message' => 'Booking successful.']);
    } else {
        http_response_code(500);
        echo json_encode(['message' => 'Database error: ' . $stmt->error]);
    }

    $stmt->close();
    $conn->close();
} else {
    http_response_code(400);
    echo json_encode(['message' => 'Invalid request method.']);
}
