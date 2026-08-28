package com.codingshuttle.youtube.hospitalManagement.service;

import com.codingshuttle.youtube.hospitalManagement.entity.Appointment;
import com.codingshuttle.youtube.hospitalManagement.entity.Doctor;
import com.codingshuttle.youtube.hospitalManagement.entity.Patient;
import com.codingshuttle.youtube.hospitalManagement.repository.AppointmentRepository;
import com.codingshuttle.youtube.hospitalManagement.repository.DoctorRepository;
import com.codingshuttle.youtube.hospitalManagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private  final PatientService patientService;
    private final PatientRepository patientRepository;

    @Transactional
    public  void createNewAppointment(Appointment appointment, Long doctorId, Long patientId){
        Doctor doctor= doctorRepository.findById(patientId).orElseThrow();
        Patient patient= patientRepository.findById(patientId).orElseThrow();

        if(appointment.getId()!=null) throw new IllegalArgumentException("Appointment already exists");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

         patient.getAppointments().add(appointment);
         appointmentRepository.save(appointment);
    }
}
