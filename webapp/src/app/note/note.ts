export class Note {
  id: number;
  patientId: number;
  createdAt: Date;
  updatedAt: Date;
  noteContent: string;

  constructor(obj?: Partial<Note>) {
    Object.assign(this, obj);
  }
}
