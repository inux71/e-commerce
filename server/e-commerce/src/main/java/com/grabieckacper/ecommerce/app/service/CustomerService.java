package com.grabieckacper.ecommerce.app.service;

import com.grabieckacper.ecommerce.app.model.Cart;
import com.grabieckacper.ecommerce.app.model.Customer;
import com.grabieckacper.ecommerce.app.model.Profile;
import com.grabieckacper.ecommerce.app.repository.CartRepository;
import com.grabieckacper.ecommerce.app.repository.CustomerRepository;
import com.grabieckacper.ecommerce.app.repository.ProfileRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final ProfileRepository profileRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(
            CustomerRepository customerRepository, ProfileRepository profileRepository, CartRepository cartRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.customerRepository = customerRepository;
        this.profileRepository = profileRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer getMe() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String email = authentication.getName();

        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));
    }

    @Transactional
    public void createCustomer(String email, String password, String firstName, String lastName) {
        customerRepository.findByEmail(email)
                .ifPresent(_ -> {
                    throw new EntityExistsException("Customer with email " + email + " already exists");
                });

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(passwordEncoder.encode(password));

        Profile profile = new Profile();
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setCustomer(customer);
        customer.setProfile(profile);

        profileRepository.save(profile);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);

        cartRepository.save(cart);

        customerRepository.save(customer);
    }

    public void changePassword(String password) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String email = authentication.getName();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()  -> new UsernameNotFoundException("User with email: " + email + " not found"));
        customer.setPassword(passwordEncoder.encode(password));

        customerRepository.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByEmail(username)
                .orElseThrow(()  -> new UsernameNotFoundException("User with email: " + username + " not found"));
    }
}
