<?php

header('Content-Type: application/json');
require 'db.php'; // Include database connection

if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $user_id = $_GET['user_id'] ?? '';

    // Validate user_id
    if (empty($user_id)) {
        http_response_code(400);
        echo json_encode(['message' => 'User ID is required.']);
        exit;
    }

    // Fetch bookings for the given user_id
    $stmt = $conn->prepare("SELECT id, user_id, table_no, starting_time, ending_time, booking_date, amount, transaction_id 
                            FROM tbl_bookings WHERE user_id = ?");
    $stmt->bind_param("i", $user_id);
    $stmt->execute();
    
    $result = $stmt->get_result();
    $bookings = [];

    while ($row = $result->fetch_assoc()) {
        $bookings[] = $row;
    }

    if (count($bookings) > 0) {
        http_response_code(200);
        echo json_encode(['success' => true, 'bookings' => $bookings]);
    } else {
        http_response_code(404);
        echo json_encode(['success' => false, 'message' => 'No bookings found for this user.']);
    }

    $stmt->close();
    $conn->close();
} else {
    http_response_code(400);
    echo json_encode(['message' => 'Invalid request method.']);
}
