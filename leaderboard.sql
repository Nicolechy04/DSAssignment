CREATE TABLE quiz_results (
    student_name VARCHAR(100),
    marks INT,
    time_finished DATETIME
);
INSERT INTO quiz_results (student_name, marks, time_finished) 
VALUES 
('John Doe', 90, '2024-04-30 15:30:00'),
('Jane Smith', 85, '2024-04-30 16:00:00'),
('Alice Johnson', 95, '2024-04-30 16:30:00'),
('Michael Brown', 88, '2024-04-30 17:00:00'),
('Emily Wilson', 92, '2024-04-30 17:30:00'),
('David Lee', 87, '2024-04-30 18:00:00'),
('Sarah Davis', 91, '2024-04-30 18:30:00'),
('Ryan Martinez', 89, '2024-04-30 19:00:00'),
('Jessica Taylor', 93, '2024-04-30 19:30:00'),
('Christopher Garcia', 86, '2024-04-30 20:00:00');
SELECT * FROM quiz_results;
