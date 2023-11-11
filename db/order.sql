-- products.orders definition

CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `customer_id` int NOT NULL,
  `created_date_time` datetime NOT NULL,
  `updated_date_time` datetime NOT NULL,
  `quantity_purchased` int NOT NULL,
  `is_cancel` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `orders_product_id_IDX` (`product_id`) USING BTREE,
  KEY `orders_customer_id_IDX` (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;