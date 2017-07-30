module lock_1 (CLOCK_50,
sw0,sw1,sw2,sw3,sw4,sw5,sw6,sw7,sw8,sw9,
key3,key2,key1,key0,
hex5,hex4,hex3,hex2,hex1,hex0
);
input CLOCK_50;
input sw0,sw1,sw2,sw3,sw4,sw5,sw6,sw7,sw8,sw9;
input key3,key2,key1,key0;

output [6:0] hex5,hex4,hex3,hex2,hex1,hex0;
reg [31:0] key3_counter,key2_counter,key1_counter,key0_counter,run_counter;
reg [9:0] pw,guessnum;          //预设的密码
reg [3:0] guess2,guess1,guess0; //输入的百十个猜测数
reg [3:0] display5,display4,display3,display2,display1,display0;
reg [1:0] state;
reg back;

   sevenseg led_display0(display0, hex0);
	sevenseg led_display1(display1, hex1);
	sevenseg led_display2(display2, hex2);
	sevenseg led_display3(display3, hex3);
	sevenseg led_display4(display4, hex4);
	sevenseg led_display5(display5, hex5);
	
   initial begin
		key3_counter <= 0; key2_counter <= 0; key1_counter <= 0; key0_counter <= 0; 
		run_counter <= 0;
		guess2 <= 4'b0; guess1 <= 4'b0; guess0 <= 4'b0;
		display0 <= 4'd0; display1 <= 4'd0; display2 <= 4'd0;
		display3 <= 4'd15; display4 <= 4'd15; display5 <= 4'd15;
		state <= 2'b00; back <= 1'b1;
	end
	
	always @ (sw0,sw1,sw2,sw3,sw4,sw5,sw6,sw7,sw8,sw9) begin
	  pw = {sw9,sw8,sw7,sw6,sw5,sw4,sw3,sw2,sw1,sw0};
	end

	always @ (posedge CLOCK_50) 
	begin
		if (key0 == 0) key0_counter <= key0_counter + 1;
			else key0_counter <= 0;
		if (key0_counter == 500000) begin
        guess0 = guess0 + 4'd1; guess0 = guess0 %  10; 
		end
		
		if (key1 == 0) key1_counter <= key1_counter + 1;
			else key1_counter <= 0;
		if (key1_counter == 500000) begin
        guess1 = guess1 + 1; guess1 = guess1 % 10; 
		end
		
		if (key2 == 0) key2_counter <= key2_counter + 1;
			else key2_counter <= 0;
		if (key2_counter == 500000) begin
        guess2 = guess2 + 1; guess2 = guess2 % 11; 
		end
		
		if (state == 2'b00) begin
			display0 <= guess0; display1 <= guess1; 
			display2 <= guess2 % 10; display3 <= guess2 / 10;
			display4 <= 4'd15; display5 <= 4'd15;
		end 
		guessnum = guess2*100 + guess1*10 + guess0; 
		
		if (key3 == 0) key3_counter <= key3_counter + 1;
			else key3_counter <= 0;
		if (key3_counter == 500000) 
		begin
          state = (guessnum == pw)?2'b10:2'b01;
		    if (state == 2'b10) begin
		        back = 1 - back;
				  if (back == 1'b0) begin
					  display5 <= 4'd15; display4 <= 4'd15; display3 <= 4'd10; 
					  display2 <= 4'd11; display1 <= 4'd5; display0 <= 4'd5; 
				  end else state <= 2'b00;
		    end else if (state == 2'b01) begin
		        back = 1 - back;
				  if (back == 1'b0) begin
				     display5 <= 4'd15; display4 <= 4'd15; display3 <= 4'd12; 
					  display2 <= 4'd11; display1 <= 4'd1; display0 <= 4'd13;
				  end else state <= 2'b00;
		    end 
		end
		
		if (back == 1'b0) begin
			run_counter = run_counter + 1;
			if (run_counter == 1000000) begin
				display5 <= display4; display4 <= display3; display3 <= display2;
				display2 <= display1; display1 <= display0; display0 <= display5;
				run_counter <= 0;
			end
		end
		
	end
   
endmodule





module sevenseg ( data, ledsegments);
input [3:0] data;
output ledsegments;
reg [6:0] ledsegments;
always @ (*)
case(data)
0: ledsegments = 7'b100_0000;
1: ledsegments = 7'b111_1001;
2: ledsegments = 7'b010_0100;
3: ledsegments = 7'b011_0000;
4: ledsegments = 7'b001_1001;
5: ledsegments = 7'b001_0010;
6: ledsegments = 7'b000_0010;
7: ledsegments = 7'b111_1000;
8: ledsegments = 7'b000_0000;
9: ledsegments = 7'b001_0000;
10:ledsegments = 7'b000_1100;//P-10
11:ledsegments = 7'b000_1000;//A-11 S-5
12:ledsegments = 7'b000_1110;//F-12
13:ledsegments = 7'b100_0111;//L-13
default: ledsegments = 7'b111_1111; // 其它值时全灭。
endcase
endmodule
