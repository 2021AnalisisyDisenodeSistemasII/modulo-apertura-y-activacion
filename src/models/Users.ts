export class User {
  private id: string;
  public name: string;
  public phone: number;
  public occupation: string;

  constructor(name: string, phone: number, occupation: string) {
    this.name = name;
    this.phone = phone;
    this.occupation = occupation;
    this.id = Date.now().toString();
  }
}

export class NaturalUser extends User {
  constructor(name: string, phone: number, occupation: string) {
    super(name, phone, occupation);
  }
}

export class JuridicUser extends User {
  constructor(name: string, phone: number, occupation: string) {
    super(name, phone, occupation);
  }
}
