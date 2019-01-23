type::{
  not: string,
  type: $any,   // overrides default "type: any"
}
valid::[
  {{aGVsbG8=}},
  false,
  {{"hello"}},
  12.345,
  12e3,
  12345,
  hello,
  2018-01-01T,
  [],
  (),
  {},
  null,
  null.null,
  null.blob,
  null.bool,
  null.clob,
  null.decimal,
  null.float,
  null.int,
  null.list,
  null.sexp,
  null.string,
  null.struct,
  null.symbol,
  null.timestamp,
]
invalid::[
  "hello",
]

type::{
  not: $string,
  type: $any,   // overrides default "type: any"
}
valid::[
  {{aGVsbG8=}},
  false,
  {{"hello"}},
  12.345,
  12e3,
  12345,
  hello,
  2018-01-01T,
  [],
  (),
  {},
  null,
  null.null,
  null.blob,
  null.bool,
  null.clob,
  null.decimal,
  null.float,
  null.int,
  null.list,
  null.sexp,
  null.struct,
  null.symbol,
  null.timestamp,
]
invalid::[
  "hello",
  null.string,
]

type::{
  not: { codepoint_length: 3 },
}
valid::[
  '',
  a,
  ab,
  abcd,
  "",
  "a",
  "ab",
  "abcd",
]
invalid::[
  abc,
  "abc",
]

type::{
  not: { not: { not: { not: string } } }
}
valid::[
  "abc",
]
invalid::[
  {{aGVsbG8=}},
  false,
  {{"hello"}},
  12.345,
  12e3,
  12345,
  hello,
  2018-01-01T,
  [],
  (),
  {},
  null,
  null.null,
  null.blob,
  null.bool,
  null.clob,
  null.decimal,
  null.float,
  null.int,
  null.list,
  null.sexp,
  null.string,
  null.struct,
  null.symbol,
  null.timestamp,
]