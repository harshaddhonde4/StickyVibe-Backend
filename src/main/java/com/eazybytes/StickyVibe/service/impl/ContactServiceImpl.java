package com.eazybytes.StickyVibe.service.impl;


import com.eazybytes.StickyVibe.dto.ContactRequestDto;
import com.eazybytes.StickyVibe.entity.Contact;
import com.eazybytes.StickyVibe.repository.ContactRepository;
import com.eazybytes.StickyVibe.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;


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
        entity.setId(contactRequestDto.getId());
        entity.setName(contactRequestDto.getName());
        entity.setEmail(contactRequestDto.getEmail());
        entity.setPhone(contactRequestDto.getPhone());
        entity.setMessage(contactRequestDto.getMessage());
        return entity;
    }
}
