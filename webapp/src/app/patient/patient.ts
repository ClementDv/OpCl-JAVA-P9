export class Patient {
  id: number;
  firstName: string;
  lastName: string;
  gender: string;
  address: string;
  phoneNumber: string;
  birthDate: string;

  constructor(obj?: Partial<Patient>) {
    Object.assign(this, obj);
  }

}
