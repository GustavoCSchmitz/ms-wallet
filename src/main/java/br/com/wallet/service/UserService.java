package br.com.wallet.service;

import br.com.wallet.client.UserClient;
import br.com.wallet.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserClient userClient;
    
    public UserResponseDto getUser(String id){
        return getUserByClient(id);
    }

    private UserResponseDto getUserByClient(String id) {
        return userClient.getUser(id)
                .blockOptional()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
