package business;

import dataAccess.entity.AdminProfile;

public class AdminService {

    public AdminProfile updateName(AdminProfile profile, String newName) throws ProfileException {
        ProfileService profileService = new ProfileService();
        return new AdminProfile(profileService.updateName(profile, newName));
    }

    public AdminProfile updateAddress(AdminProfile profile, String newAddress) throws ProfileException {
        ProfileService profileService = new ProfileService();
        return new AdminProfile(profileService.updateAddress(profile, newAddress));
    }
}
