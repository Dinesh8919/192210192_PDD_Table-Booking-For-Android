<?php
header('Content-Type: application/json');
require 'db.php'; // Database connection file

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $user_id = $_POST['user_id'] ?? '';
    $booking_date = $_POST['booking_date'] ?? '';

    // Validation
    if (empty($user_id) || empty($booking_date)) {
        http_response_code(400);
        echo json_encode(['status' => 400, 'message' => 'User ID and Booking Date are required.']);
        exit;
    }

    // Validate date format (YYYY-MM-DD)
    if (!preg_match('/^\d{4}-\d{2}-\d{2}$/', $booking_date)) {
        http_response_code(400);
        echo json_encode(['status' => 400, 'message' => 'Invalid date format. Use YYYY-MM-DD.']);
        exit;
    }

    // Fetch Bookings
    $stmt = $conn->prepare("SELECT id, table_no, starting_time, ending_time, booking_date, amount, transaction_id 
                            FROM tbl_bookings 
                            WHERE user_id = ? AND booking_date = ?");
    $stmt->bind_param("is", $user_id, $booking_date);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $bookings = [];
        while ($row = $result->fetch_assoc()) {
            $bookings[] = $row;
        }
        http_response_code(200);
        echo json_encode(['status' => 200, 'message' => 'Bookings found.', 'bookings' => $bookings]);
    } else {
        http_response_code(404);
        echo json_encode(['status' => 404, 'message' => 'No bookings found for the selected date.']);
    }

    $stmt->close();
    $conn->close();
} else {
    http_response_code(405);
    echo json_encode(['status' => 405, 'message' => 'Invalid request method.']);
}
