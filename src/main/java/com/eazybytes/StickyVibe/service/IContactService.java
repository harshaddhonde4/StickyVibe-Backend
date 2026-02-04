package com.eazybytes.StickyVibe.service;

import com.eazybytes.StickyVibe.dto.ContactRequestDto;
import com.eazybytes.StickyVibe.dto.ContactResponseDto;
import com.eazybytes.StickyVibe.dto.ProductDto;

import java.util.List;

//Built service Layer with interface and implementation classes- Good Approach
public interface IContactService
{
    boolean saveContact(ContactRequestDto contactRequestDto);
    List<ContactResponseDto> getAllOpenMessages();
    void updateMessageStatus(Long contactId , String status);
}
