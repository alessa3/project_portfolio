import { sql } from '@vercel/postgres';
import { NextResponse } from 'next/server';

export async function GET(request:Request) {

    // gets user information
    const { searchParams } = new URL(request.url);
    const userName = searchParams.get('userName');
    const userEmail = searchParams.get('eMail');
    const userComment = searchParams.get('userComment');

    // tries to insert values into database
    try {
        // first checks if user email already exists
        let result =
            await sql`SELECT name FROM usercontact WHERE email = ${userEmail};`;

        // if it doesn't exist, then try to insert user contact to the database
        if(result.rowCount ==  0){
            await sql`INSERT INTO usercontact (name, email) VALUES (${userName}, ${userEmail});`;
        }

        // try to insert user comment into database
        result = await sql`INSERT INTO usercomment (userid, comment) VALUES (${userEmail}, ${userComment});`;

        // returns the result of the method
        return NextResponse.json({ result }, { status: 200 });
    } catch (error) {
        return NextResponse.json({ error }, { status: 500 });
    }
}
