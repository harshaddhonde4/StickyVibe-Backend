package com.eazybytes.StickyVibe.service.impl;


import com.eazybytes.StickyVibe.constants.ApplicationConstants;
import com.eazybytes.StickyVibe.dto.ContactRequestDto;
import com.eazybytes.StickyVibe.dto.ContactResponseDto;
import com.eazybytes.StickyVibe.entity.Contact;
import com.eazybytes.StickyVibe.exception.ResourceNotFoundException;
import com.eazybytes.StickyVibe.repository.ContactRepository;
import com.eazybytes.StickyVibe.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Service//Telling Spring that this is a Service Layer
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService
{
    private final ContactRepository contactRepository;

    @Override
    public boolean saveContact(ContactRequestDto contactRequestDto)
    {
            Contact contact = transfromToEntity(contactRequestDto);
            contactRepository.save(contact);
            return true;
    }

    //Mapper Logic for DTO
    private Contact transfromToEntity(ContactRequestDto contactRequestDto)
    {
        Contact entity = new Contact();
        entity.setContactId(contactRequestDto.getId());
        entity.setName(contactRequestDto.getName());
        entity.setEmail(contactRequestDto.getEmail());
        entity.setPhone(contactRequestDto.getPhone());
        entity.setMessage(contactRequestDto.getMessage());
        entity.setStatus(ApplicationConstants.OPEN_MESSAGE);
        entity.setCreatedBy("Anonymous"); // Set a default value for created_by
        return entity;
    }

    @Override
    public List<ContactResponseDto> getAllOpenMessages() {
        List<Contact> contacts = contactRepository.findByStatus(ApplicationConstants.OPEN_MESSAGE);
        return contacts.stream().map(this::mapToContactResponseDto).collect(Collectors.toList());
    }

    private ContactResponseDto mapToContactResponseDto(Contact contact) {
        return new ContactResponseDto(contact.getContactId(), contact.getName(),
                contact.getEmail(), contact.getPhone(), contact.getMessage(),
                contact.getStatus());
    }

    @Override
    public void updateMessageStatus(Long contactId, String status)
    {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact", "ContactId", contactId.toString()));
        contact.setStatus(status);
        contactRepository.save(contact);
    }
}
