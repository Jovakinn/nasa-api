package com.example.coolapp.service;

import com.example.coolapp.dto.PictureSubmissionRequest;
import com.example.coolapp.dto.User;
import com.example.coolapp.exceptions.CorrectPictureAlreadySubmittedException;
import com.example.coolapp.exceptions.IncorrectPictureException;
import com.example.coolapp.exceptions.InvalidUserDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class PictureService {
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final NasaClientService nasaClientService;

    public void submit(PictureSubmissionRequest request){
        validateUserAlreadySubmitted(request);
        User user = validateUserData(request);
        validatePicture(request, user);
    }

    private void validatePicture(PictureSubmissionRequest request, User user) {
        var picture = request.pictureSubmission().picture();
        log.info("Calling NASA to get the largest picture URL...");
        long start = System.nanoTime();
        var correctPicture = nasaClientService.getLargestPicture();
        log.info("Got picture in " + (System.nanoTime() - start) / 1000_000_000 + " seconds");
        if (picture.equals(correctPicture)) {
            users.put(request.address(), user);
        } else {
            throw new IncorrectPictureException();
        }
    }

    private User validateUserData(PictureSubmissionRequest request) {
        var user = request.pictureSubmission().user();
        if (ObjectUtils.isEmpty(user.firstName()) || ObjectUtils.isEmpty(user.lastName())) {
            throw new InvalidUserDataException();
        }
        return user;
    }

    private void validateUserAlreadySubmitted(PictureSubmissionRequest request) {
        if (users.containsKey(request.address())) {
            throw new CorrectPictureAlreadySubmittedException();
        }
    }
}
