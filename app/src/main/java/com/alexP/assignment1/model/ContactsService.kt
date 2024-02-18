package com.alexP.assignment1.model


import com.github.javafaker.Faker

typealias ContactsListener = (contacts: List<Contact>) -> Unit

class ContactsService {

    private var contacts = mutableListOf<Contact>()
        set(newValue) {
            field = newValue.sortedWith(compareByDescending { it.id }).toMutableList()
        }

    private val listeners = mutableListOf<ContactsListener>()

    init {
        val faker = Faker.instance()
        contacts = (0..40).map {
            Contact(
                id = it.toLong(),
                phone = faker.phoneNumber().phoneNumber(),
                photo = IMAGES[it % IMAGES.size],
                fullName = faker.name().name(),
                career = faker.job().title(),
                email = faker.internet().emailAddress(),
                address = faker.address().streetAddress(),
                dateOfBirth = faker.date().birthday().toString()
            )
        }.toMutableList()
    }

    fun deleteContact(contact: Contact) {
        val indexToDelete = contacts.indexOfFirst { it.id == contact.id }
        if (indexToDelete != -1) {
            contacts = ArrayList(contacts)
            contacts.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun addContact(contact: Contact) {
        val newContacts = ArrayList(contacts)
        newContacts.add(0, contact)
        contacts = ArrayList(newContacts)
        notifyChanges()
    }

    fun getNewId(): Long {
        return contacts.first().id + 1
    }

    fun addListener(listener: ContactsListener) {
        listeners.add(listener)
        listener.invoke(contacts)
    }

    fun removeListener(listener: ContactsListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(contacts) }
    }

    companion object {
        private val IMAGES = mutableListOf(
            "https://s3-alpha-sig.figma.com/img/ddfb/d9cc/d761bf491e6218d83cd570ef902b3e61?Expires=1708905600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=WcVy1dd117Y1KaFoeeALs7PL3nTo8r8KtQ0sOgFhFsDmDSgBrQJj-qqygA-5wKBHCl8cNkmv2soVjGgASVzOIqK4N7G-lRkAqPmETL2u-1XoqXqAjW8QetqcrgY52jxXk0207attyrTbiHQkp3CHp9GkM72CFYeEv1SJMo4ft6jL0YPGH5dPdAAv~-jrI0K2Yojp0ltnIJN-eQCP1in-1LR8sI3ZqGytK71ou9utB-PiZgMjtz1gVmBpdKehNLiKF9gJxbsx3m7yTUcP0fj9vb-1OZkKjM4KxtJ1WYecRGXqqyx8GEpA80qrws9kbNM2p8yP0wZdl0bi7to5LqTaPA__",
            "https://s3-alpha-sig.figma.com/img/56b7/6275/7dbde4af75876f40325a23557d89e229?Expires=1708905600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=UkYvijXJS3i-vdlM6TmE7GcdGifWoHb29yw3cferOGFXfKEXZdtlaZeXWLzcCk9mp7txFSqUDG8l3gdtm9TCQX05bg4Br9fJE4muoAEMhs2MILHVgq09MNosXB4CLEBJSBP~gKKD3Dm6oBwPYLf11ftxLK-W1-pJUo4ldv3v9alTZqbXDDyt3rpYR-KqWHbuHX9rNlzpCvYSMPAOqPHS852iXY0oNeWWlOqDLl80ZXPzkJrKlrGE1xA2YyGSO62WpEphBrBTDbovvjGpeEHUGLyUJjejr5T1KQHxIBMO~iUuDbrbONIszu7NwBF9GwMZ0ByPjVa4DaUl~a~ptgPrJA__",
            "https://s3-alpha-sig.figma.com/img/9cb3/6565/3f014296587cb4bbbdc01ec61b96df35?Expires=1708905600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=nuS0PrZIA0LVT0~M0tVZ7Lf-~6cAY06u3cb-2fuSpRABCpDTG-Pr3DBiXFhjZ-0BkwMlo~hH90W4XHv3dK1ZxzWt2~M3F0hJQykuGaGl5GXwc82cYOEVk0fDjjJY7GJ~ATOti6WfU322t6rgqwwEmSlzBnghPmXU13DlOMEC8D1ijlCDZEOakFQydQ6a-kAZIoFHq0KIh7eWc1ZWQcGD8GyrTCN8onlVPUdlvhZFtKeIVeA72NT4zwuRLwtaejr5D0r-IwyQmnDjXh~acP2pgUBlmv-bCwyPqHtpBjKJO9bKvw2f8y~RT~GsMDcafeTWXy8STk8E6Iy-DOMQngY3ZA__",
            "https://s3-alpha-sig.figma.com/img/7a18/e69e/ca34ee41b570f1948b4e8373ad826041?Expires=1708905600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=G1Q0QBFTnVbZ9b7ObmK6~PfIkg~SzJ0gQAQfR2UQje6OaL6Lbvb4fWvUTI6gQgbxnxwCSZxEa3lkxOBuU7gBy9pIvvMx8IREsw68Ij-Tnss4Hqw4g6HFRkcOpbjUt~O9zPUnnZpQbCbEqJkNWsBGyaiys4T5S2yPeu68HUZu9N-GA9TxsLTzVW6RNt5QC7t3L-nfDyjJGSDFIMIHp0nhfRj8FQgOlqUV9nw82cYxel0eSDSWDlx-XVyG2r9HxMrig~oi9WzNwT8qvB~NtYTGW16TOpFqC1fgdsPmwDx989BhqmnB1-mtZsWOFebkB7kH4efR5i2dCg6Lhi6KSKf5jA__",
            "https://s3-alpha-sig.figma.com/img/8e2a/b032/97b0c902250bf8f29057cc70335c63b6?Expires=1708905600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=iYVJogVfBGYRa4DSWeMaUs3mmz~aFPldUtGz1UjphVxkmHWKcJ2b7ul3yYOjjDnGsVYzkrWCPN6n~Y9SV67GPQIrxI8Nis4b7nSsDIA7Qk5UVs~OjkssxsIc1aeDgCE5OAyahW8UrziYvz7CTwIgK0iMh5YChDuSIXZlxnCfufF3W5V-7imYgyNbrg39hZAowjBplMj66VYDYihl5oXbspQJAAL2yeEPSFFGztEv~AZ~yn0CPxGAq8oYfWnEAAgC8Sf5uZU~BL7KbyNvgyENe4CUQI0SsOJYG2ne2PIWKKDRpNXJpXkLHwNIcaSpdH1PLaLrbGX5jNqYXYUnfoZpAA__",
            "https://s3-alpha-sig.figma.com/img/28f8/2ef8/a69cbd032a9d647f1e70df95cbb3448b?Expires=1708905600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=e2n52-D-XxFx6RpAU~EqdUE7e-RhRzVcFRthhpBc9NQ7kKlVBVoEFu4wcIfvrSt5Onj~MSPnRk17ncP4FHzNW4NF4Rk0nOJDEIFBU5gFBt8UmlOIqbUYh~~9U-n~yHIr5oxD-gP5ABlaWmomhXMXBVNMEzgsoHcxzuFFawwlxhkNBQiiIsOWtG~QXbGX~XCciZwdfhgoOIv5kYJs8HR4J2sIwWBlZk2w-HvoUds0MNilvNLP0ZFE1oWAYW8zoOfvKlTkqbskRvVKV8XF6kXdpVcPVGyQgcZSPDrC6xFbmxdw9Y8u0T-45M7hHeOQsaDJVaOOSBDavP4szearjiRaeA__"
        )
    }
}