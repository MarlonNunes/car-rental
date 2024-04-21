package com.marlonnunes.carrental.dto.contact;

import com.marlonnunes.carrental.model.Contact;
import com.marlonnunes.carrental.model.enums.ContactType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public record ContactDTO (
    Long id,
    ContactType type,

    String value
){

    public static List<ContactDTO> fromContactList(Collection<Contact> contactList) {

        return contactList.stream().filter(c -> !c.isDisabled()).map(ContactDTO::fromContact).toList();
    }

    private static ContactDTO fromContact(Contact contact){
        return new ContactDTO(
                contact.getId(),
                contact.getType(),
                contact.getValue()
        );
    }
}
