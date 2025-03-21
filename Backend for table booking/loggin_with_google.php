<?php
session_start();
require 'db.php'; // Database connection

if ($_SERVER["REQUEST_METHOD"] == "POST") {

    if(!isset($_POST['token'])) {
        http_response_code(400);
        echo json_encode(["status" => 400, "message" => "Missing Body"] );
        exit;
    }

        try {
            
            $auth_token = trim($_POST['token']);

            $my_client_id = "1062686754208-0oumjmaf09tttshh7h275udupu0qbs75.apps.googleusercontent.com";
            $url = "https://oauth2.googleapis.com/tokeninfo?id_token=" . urlencode($auth_token);
            
            $response = @file_get_contents($url);

            if($response === false) {
                http_response_code(500);
                echo json_encode(["status" => 500, "message" => "Auth Response error"]);
                exit;
            }

            $data = json_decode($response, true); // ✅ Correct JSON decoding

            // return json_encode($response, true);

            if (!$data || !isset($data['aud'])) {
                http_response_code(400);
                echo json_encode(["status" => 400, "message" => "Invalid token"]);
                exit;
            }

            if ($data['aud'] !== $my_client_id) {
                http_response_code(403);
                echo json_encode(["status" => 403, "message" => "Unauthorized token"]);
                exit;
            }

            if(!(boolean)$data['email_verified']) {
                http_response_code(403);
                echo json_encode(["status" => 403, "message" => "Email verification failed"]);
                exit;
            }

            $email     =  $data['email'];
            $name      = $data['name'];
            $password  = "welcome";
            $usertype = 100;

            $stmt = $conn -> prepare("INSERT INTO users (email, name, password, usertype) VALUES 
            (?, ?, ?, ?) ON DUPLICATE KEY UPDATE name=?");
            $stmt -> bind_param("sssis", $email, $name, $password, $usertype, $name);
            $stmt -> execute();
            
            // return json_encode(["status" => 200, "aud" => $data['aud'],
            //  "email" => $data['email'], "email_verified"=>$data['email_verified']]);

            $login_stmt = $conn->prepare("SELECT id, name, email, password,usertype FROM users WHERE email = ?");
            $login_stmt->bind_param("s", $email);
            $login_stmt->execute();
            $login_stmt->store_result();

            // $stmt->bind_result($id, $name, $db_email,$usertype);
            $login_stmt->fetch();

            // $user = [
            //     "id" => $id,
            //     "name" => $name,
            //     "email" => $db_email,
            //     "usertype"=>$usertype
            // ];

            http_response_code(200);
            echo json_encode(["status" => 200, "message" => "Success", "user" => $login_stmt]);

            exit;
    } catch (Exception $th) {
        http_response_code(500);
        echo json_encode(["status" => 500, "message" => "Internal Server Error : ". $th -> getMessage()]);
    }
} else {
    http_response_code(400);
    echo json_encode(["status" => 400, "message" => "Method Not Allowed"]);
}
?>
