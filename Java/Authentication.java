package All;

class Authentication {
    private UserManager userManager;

    public Authentication(UserManager userManager) {
        this.userManager = userManager;
    }

    public User login(String username, String password) {
        User user = userManager.getUser(username);
        if (user != null && user.login(password)) {
            System.out.println("Login successful!");
            return user;
        } else {
            System.out.println("Invalid username or password.");
            return null;
        }
    }
}

//
