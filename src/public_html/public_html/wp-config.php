<?php
/**
 * The base configuration for WordPress
 *
 * The wp-config.php creation script uses this file during the installation.
 * You don't have to use the web site, you can copy this file to "wp-config.php"
 * and fill in the values.
 *
 * This file contains the following configurations:
 *
 * * Database settings
 * * Secret keys
 * * Database table prefix
 * * ABSPATH
 *
 * @link https://wordpress.org/support/article/editing-wp-config-php/
 *
 * @package WordPress
 */

// ** Database settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define( 'DB_NAME', 'i8976739_wp1' );

/** Database username */
define( 'DB_USER', 'i8976739_wp1' );

/** Database password */
define( 'DB_PASSWORD', 'K.WbJKvXC54U8QYVblT30' );

/** Database hostname */
define( 'DB_HOST', 'localhost' );

/** Database charset to use in creating database tables. */
define( 'DB_CHARSET', 'utf8' );

/** The database collate type. Don't change this if in doubt. */
define( 'DB_COLLATE', '' );

/**#@+
 * Authentication unique keys and salts.
 *
 * Change these to different unique phrases! You can generate these using
 * the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}.
 *
 * You can change these at any point in time to invalidate all existing cookies.
 * This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define('AUTH_KEY',         'gqtbwVHdS3adrE69rYdToVfCKRwVG6gZ3k3bd3sfM9Z3ExacrMcF1WIh2V42qhDl');
define('SECURE_AUTH_KEY',  'O6Um80AjFX3cGOZwoK619vr21Ao2HNd64Bzf3KBwZWDUrJqZEwFQTkq1cXWOv4ba');
define('LOGGED_IN_KEY',    'PwQjRq9ODAijGKijHPW2UrPo8ZEBXYwLGkSY6D8ACkJ3jrewzT8z6g1nuzgEB9Uc');
define('NONCE_KEY',        'sRPNQKDodqG9Ztn6NK77WlqMCUj95QWWShY63G6zI572OACm9i0wDl92ZZEHF8Mz');
define('AUTH_SALT',        'dUUx8wLV90qkxbi7RZRg3D0ncJqbPv4aesDbXgNCFR0RE7H2AjW1ttJ681Q5ar5d');
define('SECURE_AUTH_SALT', '2HZyDZLM8OepxjzdSNtQFRlRF153omuBaH1fLUvFlIvczi7uATaIAOAB8fb2EPEw');
define('LOGGED_IN_SALT',   'JCooWh2FgvEMHfopmlL9bf4wXdEDllVtGWy2PwdGnyfpW8bKKk0xvCujKn6WoieF');
define('NONCE_SALT',       '4EOnm9Nr21o4Qyc6cVm8iiBQFvcPGKL8GjfOASpZkx5lxvjHR2SPsHKjAABjV1ws');

/**
 * Other customizations.
 */
define('FS_METHOD','direct');
define('FS_CHMOD_DIR',0755);
define('FS_CHMOD_FILE',0644);
define('WP_TEMP_DIR',dirname(__FILE__).'/wp-content/uploads');


/**#@-*/

/**
 * WordPress database table prefix.
 *
 * You can have multiple installations in one database if you give each
 * a unique prefix. Only numbers, letters, and underscores please!
 */
$table_prefix = 'wp_';

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 *
 * For information on other constants that can be used for debugging,
 * visit the documentation.
 *
 * @link https://wordpress.org/support/article/debugging-in-wordpress/
 */
define( 'WP_DEBUG', false );

/* Add any custom values between this line and the "stop editing" line. */



/* That's all, stop editing! Happy publishing. */

/** Absolute path to the WordPress directory. */
if ( ! defined( 'ABSPATH' ) ) {
	define( 'ABSPATH', __DIR__ . '/' );
}

/** Sets up WordPress vars and included files. */
require_once ABSPATH . 'wp-settings.php';
