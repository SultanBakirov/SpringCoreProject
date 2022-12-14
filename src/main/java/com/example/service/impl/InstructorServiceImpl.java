package com.example.service.impl;

import com.example.models.Instructor;
import com.example.repository.InstructorRepository;
import com.example.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepository.getAllInstructors();
    }

    @Override
    public List<Instructor> getAllInstructorsByCourseId(Long courseId) {
        return instructorRepository.getAllInstructorsByCourseId(courseId);
    }
    @Override
    public void addInstructor(Long id, Instructor instructor) throws IOException {
        List<Instructor> instructors = instructorRepository.getAllInstructorsByCourseId(id);
        for (Instructor i : instructors) {
            if (i.getEmail().equals(instructor.getEmail())){
                throw new IOException("Instructor with email already exists!");
            }
        }

        validator(instructor.getPhoneNumber().replace(" ", ""), instructor.getLastName().replace(" ", ""), instructor.getFirstName().replace(" ", ""));
        instructorRepository.addInstructor(id,instructor);
    }
    @Override
    public Instructor getInstructorById(Long id) {
        return instructorRepository.getInstructorById(id);
    }
    @Override
    public void updateInstructor(Instructor instructor, Long id) throws IOException {
        validator(instructor.getPhoneNumber().replace(" ", ""), instructor.getLastName().replace(" ", ""), instructor.getFirstName().replace(" ", ""));
        instructorRepository.updateInstructor(instructor,id);
    }

    @Override
    public void deleteInstructorById(Long id) {
        instructorRepository.deleteInstructorById(id);
    }
    @Override
    public void assignInstructor(Long courseId, Long instructorId) throws IOException {
        instructorRepository.assignInstructor(courseId, instructorId);
    }
    private void validator(String phone, String firstName, String lastName) throws IOException {
        if (firstName.length()>2 && lastName.length()>2) {
            for (Character i : firstName.toCharArray()) {
                if (!Character.isAlphabetic(i)) {
                    throw new IOException("Numbers cannot be inserted in the name of the instructor");
                }
            }

            for (Character i : lastName.toCharArray()) {
                if (!Character.isAlphabetic(i)) {
                    throw new IOException("Numbers cannot be inserted into the name of the instructor");
                }
            }
        } else {
            throw new IOException("Instructor's first or last name must contain at least 3 letters");
        }

        if (phone.length()==12
                && phone.charAt(0) == '+'
                && phone.charAt(1) == '4'
                && phone.charAt(2) == '8'){
            int counter = 0;

            for (Character i : phone.toCharArray()) {
                if (counter!=0){
                    if (!Character.isDigit(i)) {
                        throw new IOException("Number format is not correct");
                    }
                }
                counter++;
            }
        }else {
            throw new IOException("Number format is not correct");
        }
    }
}
