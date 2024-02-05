package com.alexP.assignment1.model


import com.github.javafaker.Faker

typealias ContactsListener = (contacts: List<Contact>) -> Unit

class ContactsService {

    private var contacts = mutableListOf<Contact>()
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

    fun getContacts(): MutableList<Contact> {
        return contacts
    }

    fun deleteContact(contact: Contact) {
        val indexToDelete = contacts.indexOfFirst { it.id == contact.id }
        if (indexToDelete != -1) {
            contacts.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun addContact(contact: Contact) {
        contacts.add(0, contact)
        contacts = contacts.sortedWith(compareBy { it.id }).toMutableList()
        notifyChanges()
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
            "https://s3-alpha-sig.figma.com/img/ddfb/d9cc/d761bf491e6218d83cd570ef902b3e61?Expires=1707091200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=hAp86lplz0CDSEA65BuwLoBpn-vtZP0QeSY4kY-hVsbRCpnQ~6GA2~j3k~YiNbu-LNZFSrbWYlS7BxzSTu8MJIuK7Og1IBMKyBEXO4Ac5b-h55Jc5ksGd0lr8aVhWx4G0fn1DHBnEDNm46qRwrI0o0x~gfUqWAOrnkxbFJKPxa~nH0sH3lYanALixhjBL2RFlCphr9fk5~nOgXgIBguKBpm7i~mNE6wL5wncgK6FVxhMF3wAhKmGvJPaUD~sdaSJciFUrsfMEIaqu-vqHQwquYUUQhvkle7dsGRf8P~HskEOU2nzm8XS7lRIAj7FSxIedmr1FCHsrsP83ZRGKA1FPQ__",
            "https://s3-alpha-sig.figma.com/img/56b7/6275/7dbde4af75876f40325a23557d89e229?Expires=1707091200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=EiauHzlthKnC1X9UecqvhYCIjDZBQondQeZ5V7NyH48ADxsJzY2S5WNgrXaOZjKF2O4GEKq72xZ~eQkhBAXkGOWncMHI1t0jelzETrlTwSw2j9OLJEUEB60OULOwopySdqDdsdIN971F9YCCuVsewOXbhygxsOZ5gH237IH3xT6pRU8T7QiTFnnAmylKm1rvPMnB9D602PDDAg91VNlT68J7PVYHjzJD5PQGXOPt-Jxghu5Q4zTlqPDXnkiBLRXJuG-q5kny2Ue4QroyS9W1RV6sEJQBZju-VUZ93prpULiF7YNWy5sfiRq~~WDBlR-vJ67h4Mab6NYkS8fMTYI58A__",
            "https://s3-alpha-sig.figma.com/img/9cb3/6565/3f014296587cb4bbbdc01ec61b96df35?Expires=1707091200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=QchaSm9wBuV~4w9NYafhuRExp~goS4mrpz1KbvTB6yVS74XYUwn7R1RsrZW8I-bv-hERoiCuXXvpUy~NZ0kAVuvk7Efi8hID2LRIUYeaw1U91h8VDzpKbBW6clidxYGOsDn403A9142Q3ZuBquTWGRihQQZ6xfFIAY649cXA~kocRNSAcByz~Es0M7S8lVmFVu-XmvSbO5YuCTA5Ugg7i~9xwyqCMkIPzVXeuJjULMqLXPjqs-AWW5u6CxKrXJaY5fzHjXwVmtKIl9bsLa~EJ7oYpEzG4sI3xe1VXCuseX-WdxNDmlivRgRKc1sDYibeSBnpH4N-e-vwg-~7RP3C2A__",
            "https://s3-alpha-sig.figma.com/img/7a18/e69e/ca34ee41b570f1948b4e8373ad826041?Expires=1707091200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=R5y3wJyH5SzRwR4gpMrd6LsIXjI8KZ5VRsR5EdtuB~JgCIvj~TdrFU5gH6Cvgo05Zi2A4sROI3HSJPdcyBdA0hxqQyFg89aohkGFFr4R6ILTJoXYjZu4xEyQ0Yws5hhyYNG1jL-512iikLsk6Y~FWuski9UF2MsfxmEEYuP3rCDP8lyUdKkszayfvvoGWME9ektflM7lzKNhLlRCsN5YmjDCN8SseHH-zzoBFgP9QTgrlxFIpxs5wCD0uhMyNJa6AuHT20SUF3GnwMCzsOqUyzL3xmoQjLTa1NxM9cHr1GOc7RYysHdjx~MNsB5S41vhUknGwilKbjVG-5AKh4Nukw__",
            "https://s3-alpha-sig.figma.com/img/8e2a/b032/97b0c902250bf8f29057cc70335c63b6?Expires=1707091200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=VTWgv9Yfdc6XXw5UcrSMZ0ey6Z-s5Tfl~2sQbVp0uBuwivAbDZX~2tBEvCeVcKarLB2DETYBt1Oj7FHmkYbQr8sStLVa2asExamdQ~82gMRSDnTZaRJ9UM41DfYiCCz5-vzkQA2W-~NE09d1FoHXu9X04adVTz2mzzMM5VLJStbNrL0kD8qnOiBkgJuS7QyM4GxgZmsNPYvcOYRDsAjjxnhcOfjmQr5xyElvp8NCcUMOjlloLg-sPEBV~GBPmDcZwtjQlfexs9vyCaDnyko0738fFYJvGtEnfhu~kGq1F9tjwAz1GwSy7n3Wx73KnqJvwB6uCjVm0Ob7HDQimG2iaQ__",
            "https://s3-alpha-sig.figma.com/img/28f8/2ef8/a69cbd032a9d647f1e70df95cbb3448b?Expires=1707091200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=Xt7y41V1sIbZP1Z5HgbV5tdyCrPV3~MRD9wTEorZQNbBNBlc8lJDHsB~2vJP8Gr8ly0Nvusu3LuGD4BcwFKv8V7WIIs9yVGvF7wsC7~y5z9pu~e6CNHQlDNHfP3lt2qflTxbczDvBeajeKInCdHJHCVlclss4Z9yr73SD4K9jeVyALqtPSGz40EaTYBy48YbitfY1C~89b-0zAxTeSd7fcuMf1s40YQ0vdA1Hm~w7GfHL1LJi5Z6b3Wd~Ci6CW8Ol4vZ6Czr3IaEFfgCr~~NmjApYj4gYekzw4boIuAe9veVpv2dbZTVmqDhIowEZ-E3GlXxMA7eYaOig7vdor~QHg__"
        )
    }
}